/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.DogDTO;
import dto.DogSmallDTO;
import dto.OwnerDTO;
import dto.OwnerSmallDTO;
import dto.PersonDTO;
import dto.WalkerDTO;
import dto.WalkerSmallDTO;
import entities.Dog;
import entities.Owner;
import entities.Person;
import entities.Walker;
import interfaces.IDogFacade;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Tweny
 */
public class DogFacade implements IDogFacade {

    private static DogFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private DogFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static DogFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DogFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<WalkerSmallDTO> getAllWalkers() throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Walker> query = em.createQuery("SELECT w FROM Walker w", Walker.class);
            List<Walker> walkers = query.getResultList();
            System.out.println(walkers.size());
            ArrayList<WalkerSmallDTO> walkerSmallDTOs = new ArrayList<>();
            for (Walker w : walkers) {
                walkerSmallDTOs.add(new WalkerSmallDTO(w));
            }
            return walkerSmallDTOs;
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public List<WalkerSmallDTO> getAllWalkersByDogId(int dogId) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Walker> query = em.createQuery("SELECT w FROM Walker w JOIN w.dogs d WHERE d.id = :id", Walker.class);
            query.setParameter("id", dogId);
            List<Walker> walkers = query.getResultList();
            System.out.println(walkers.size());
            ArrayList<WalkerSmallDTO> walkerSmallDTOs = new ArrayList<>();
            for (Walker w : walkers) {
                walkerSmallDTOs.add(new WalkerSmallDTO(w));
            }
            return walkerSmallDTOs;
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public DogDTO addDog(DogDTO dogDTO) throws WebApplicationException {

        if (dogDTO.getName() == null || dogDTO.getName().equals("")) {
            throw new WebApplicationException("Name is missing", 401);
        } else if (dogDTO.getBreed() == null || dogDTO.getBreed().equals("")) {
            throw new WebApplicationException("Breed is missing", 401);
        } else if (dogDTO.getImage() == null || dogDTO.getImage().equals("")) {
            throw new WebApplicationException("Image is missing", 401);
        } else if (dogDTO.getGender() == null || dogDTO.getGender().equals("")) {
            throw new WebApplicationException("Gender is missing", 401);
        } else if (dogDTO.getBirthdate() == null || dogDTO.getBirthdate().equals("")) {
            throw new WebApplicationException("Birthdate is missing", 401);
        } else if (dogDTO.getWalkers() == null || dogDTO.getWalkers().size() == 0) {
            throw new WebApplicationException("Walkers are empty", 401);
        } else if (dogDTO.getOwner().getName() == null || dogDTO.getOwner().getName().equals("")) {
            throw new WebApplicationException("Owner name is empty", 401);
        } else if (dogDTO.getOwner().getAddress() == null || dogDTO.getOwner().getAddress().equals("")) {
            throw new WebApplicationException("Owner address is empty", 401);
        } else if (dogDTO.getOwner().getPhone() == null || dogDTO.getOwner().getPhone().equals("")) {
            throw new WebApplicationException("Owner phone is empty", 401);
        }

        EntityManager em = emf.createEntityManager();
//        Dog dog = doesDogExist(dogDTO);
//        if (dog.getId() > -1) {
//            dog = em.find(Dog.class, dog.getId());
//        }
        Dog dog = new Dog(dogDTO); 

        Owner owner = doesOwnerExist(dogDTO.getOwner());
        if (owner.getId() > -1) {
            owner = em.find(Owner.class, owner.getId());
        }

        try {
            em.getTransaction().begin();

//            if (dog.getId() < 0) {
//                em.persist(dog);
//            }
            em.persist(dog);
            System.out.println(dog.getId());
            

            if (owner.getId() < 0) {
                em.persist(owner);
            }

            owner.addDog(dog);

            em.merge(owner);

            //Create or use existing Walker
            if (dogDTO.getWalkers() != null) {
                Walker walker;
                for (WalkerSmallDTO walkerSmallDTO : dogDTO.getWalkers()) {
                    walker = doesWalkerExist(walkerSmallDTO);
                    if (walker.getId() > 0) {
                        walker = em.find(Walker.class, walker.getId());
                    }
                    System.out.println(walker);
                    dog.addWalkers(walker);
                }
            }
            em.merge(dog);

            em.getTransaction().commit();

            return new DogDTO(dog);
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public DogDTO editDog(DogDTO dogDTO, int dogId) throws WebApplicationException {
        if (dogDTO.getName() == null || dogDTO.getName().equals("")) {
            throw new WebApplicationException("Name is missing", 401);
        } else if (dogDTO.getBreed() == null || dogDTO.getBreed().equals("")) {
            throw new WebApplicationException("Breed is missing", 401);
        } else if (dogDTO.getImage() == null || dogDTO.getImage().equals("")) {
            throw new WebApplicationException("Image is missing", 401);
        } else if (dogDTO.getGender() == null || dogDTO.getGender().equals("")) {
            throw new WebApplicationException("Gender is missing", 401);
        } else if (dogDTO.getBirthdate() == null || dogDTO.getBirthdate().equals("")) {
            throw new WebApplicationException("Birthdate is missing", 401);
        } else if (dogDTO.getWalkers() == null || dogDTO.getWalkers().size() == 0) {
            throw new WebApplicationException("Walkers are empty", 401);
        } else if (dogDTO.getOwner().getName() == null || dogDTO.getOwner().getName().equals("")) {
            throw new WebApplicationException("Owner name is empty", 401);
        } else if (dogDTO.getOwner().getAddress() == null || dogDTO.getOwner().getAddress().equals("")) {
            throw new WebApplicationException("Owner address is empty", 401);
        } else if (dogDTO.getOwner().getPhone() == null || dogDTO.getOwner().getPhone().equals("")) {
            throw new WebApplicationException("Owner phone is empty", 401);
        }

        EntityManager em = emf.createEntityManager();
        Dog dogInDB = em.find(Dog.class, dogId);

        OwnerSmallDTO ownerSmallDTO = dogDTO.getOwner();

        try {
            em.getTransaction().begin();

            dogInDB.setName(dogDTO.getName()); //Uncomment if name is set to uniq i Dog.Class
            dogInDB.setBreed(dogDTO.getBreed());
            dogInDB.setImage(dogDTO.getImage());
            dogInDB.setGender(dogDTO.getGender());
            dogInDB.setBirthdate(dogDTO.getBirthdate());

            if (!dogInDB.getOwner().getPhone().equals(ownerSmallDTO.getPhone())) {
                Owner owner = doesOwnerExist(dogDTO.getOwner());
                if (owner.getId() > -1) {
                    owner = em.find(Owner.class, owner.getId());
                } else {
                    em.persist(owner);
                }
                owner.addDog(dogInDB);
            } else {
                Owner owner = em.find(Owner.class, dogInDB.getOwner().getId());
                owner.setName(ownerSmallDTO.getName());
                owner.setAddress(ownerSmallDTO.getAddress());
                em.merge(owner);
            }

            if (dogDTO.getWalkers() != null) {
                for (int i = 0; i < dogDTO.getWalkers().size(); i++) {
                    Walker walker;
                    int count = 0;
                    for (int j = 0; j < dogInDB.getWalkers().size(); j++) {
                        if (dogDTO.getWalkers().get(i).getPhone().equals(dogInDB.getWalkers().get(j).getPhone())) {
                            count = 1;
                        }
                    }
                    if (count == 0) {
                        walker = doesWalkerExist(dogDTO.getWalkers().get(i));
                        if (walker.getId() > 0) {
                            walker = em.find(Walker.class, walker.getId());
                        }
                        em.merge(walker);
                        dogInDB.addWalkers(walker);

                    } else {
                        walker = doesWalkerExist(dogDTO.getWalkers().get(i));
                        System.out.println(walker.getId());
                        walker = em.find(Walker.class, walker.getId());
                        walker.setName(dogDTO.getWalkers().get(i).getName());
                        walker.setAddress(dogDTO.getWalkers().get(i).getAddress());
                        em.merge(walker);
                        System.out.println("her");
                    }
                }
            }

            em.merge(dogInDB);
            em.getTransaction().commit();
            return new DogDTO(dogInDB);

        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } 
        finally {
            em.close();
        }
    }

    @Override
    public DogDTO deleteDog(int dogId) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Dog dog = em.find(Dog.class, dogId);
            em.remove(dog);
            em.getTransaction().commit();
            return new DogDTO(dog);
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new WebApplicationException("Could not delete, provided id: " + dogId + " does not exist", 404);
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public List<DogSmallDTO> getAllDogsByOwnerId(int OwnerId) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Dog> query = em.createQuery("SELECT d FROM Dog d JOIN d.owner o WHERE o.id = :id", Dog.class);
            query.setParameter("id", OwnerId);
            List<Dog> dogs = query.getResultList();
            System.out.println(dogs.size());
            ArrayList<DogSmallDTO> dogSmallDTOs = new ArrayList<>();
            for (Dog d : dogs) {
                dogSmallDTOs.add(new DogSmallDTO(d));
            }
            return dogSmallDTOs;
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public OwnerDTO addOwner(OwnerSmallDTO ownerSmallDTO) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        Owner owner = doesOwnerExist(ownerSmallDTO);
        if (owner.getId() > -1) {
            owner = em.find(Owner.class, owner.getId());
            return new OwnerDTO(owner);
        } else {
            try {
                em.getTransaction().begin();
                em.persist(owner);
                em.getTransaction().commit();
                return new OwnerDTO(owner);
            } catch (RuntimeException ex) {
                throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
            } finally {
                em.close();
            }
        }
    }

    @Override
    public DogDTO addWalkerToDog(WalkerSmallDTO walkerSmallDTO, int dogId) throws WebApplicationException {

        EntityManager em = emf.createEntityManager();
        Dog dog = em.find(Dog.class, dogId);

        try {
            em.getTransaction().begin();

            int count = 0;
            for (int i = 0; i < dog.getWalkers().size(); i++) {
                if (dog.getWalkers().get(i).getPhone().equals(walkerSmallDTO.getPhone())) {
                    count = count + 1;
                }
            }
            if (count == 0) {
                Walker walker = doesWalkerExist(walkerSmallDTO);
                if (walker.getId() > 0) {
                    walker = em.find(Walker.class, walker.getId());
                }
                System.out.println(walker);
                dog.addWalkers(walker);
            }

            em.merge(dog);

            em.getTransaction().commit();

            return new DogDTO(dog);
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public DogDTO removeWalkerToDog(WalkerSmallDTO walkerSmallDTO, int dogId) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        Dog dog = em.find(Dog.class, dogId);

        try {
            em.getTransaction().begin();

            int count = 0;
            for (int i = 0; i < dog.getWalkers().size(); i++) {
                if (dog.getWalkers().get(i).getPhone().equals(walkerSmallDTO.getPhone())) {
                    count = count + 1;
                }
            }
            if (count != 0) {
                Walker walker = doesWalkerExist(walkerSmallDTO);
                if (walker.getId() > 0) {
                    walker = em.find(Walker.class, walker.getId());
                }
                System.out.println(walker);
                dog.removeWalkers(walker);
            }

            em.merge(dog);

            em.getTransaction().commit();

            return new DogDTO(dog);
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }
    
     @Override
    public List<DogDTO> getAllDogs() throws WebApplicationException {
         EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Dog> query = em.createQuery("SELECT d FROM Dog d ", Dog.class);
            List<Dog> dogs = query.getResultList();
            System.out.println(dogs.size());
            ArrayList<DogDTO> dogDTOs = new ArrayList<>();
            for (Dog d : dogs) {
                dogDTOs.add(new DogDTO(d));
            }
            return dogDTOs;
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public List<OwnerDTO> getAllOwners() throws WebApplicationException {
          EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o ", Owner.class);
            List<Owner> owners = query.getResultList();
            System.out.println(owners.size());
            ArrayList<OwnerDTO> ownerDTOs = new ArrayList<>();
            for (Owner w : owners) {
                ownerDTOs.add(new OwnerDTO(w));
            }
            return ownerDTOs;
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public DogDTO connectOwnerWithDog(int dogId, int ownerId) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        Dog dog = em.find(Dog.class, dogId);
        Owner owner = em.find(Owner.class, ownerId);

        try {
            em.getTransaction().begin();

            owner.addDog(dog);

            em.merge(owner);

            em.getTransaction().commit();

            return new DogDTO(dog);
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    private Dog doesDogExist(DogDTO dogDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT d FROM Dog d WHERE d.name = :name ", Dog.class);
            query.setParameter("name", dogDTO.getName());
            Dog dog = (Dog) query.getSingleResult();
            return dog;
        } catch (NoResultException ex) {
            return new Dog(dogDTO);
        } finally {
            em.close();
        }
    }

    private Walker doesWalkerExist(WalkerSmallDTO walkerSmallDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT w FROM Walker w WHERE w.phone = :phone ", Walker.class);
            query.setParameter("phone", walkerSmallDTO.getPhone());
            Walker walker = (Walker) query.getSingleResult();
            return walker;
        } catch (NoResultException ex) {
            return new Walker(walkerSmallDTO);
        } finally {
            em.close();
        }
    }

    private Owner doesOwnerExist(OwnerSmallDTO ownerSmallDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT o FROM Owner o WHERE o.phone = :phone ", Walker.class);
            query.setParameter("phone", ownerSmallDTO.getPhone());
            Owner owner = (Owner) query.getSingleResult();
            return owner;
        } catch (NoResultException ex) {
            return new Owner(ownerSmallDTO);
        } finally {
            em.close();
        }
    }
}
