/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.HobbyDTO;
import dto.JobDTO;
import dto.NickNameDTO;
import dto.PersonDTO;
import entities.Hobby;
import entities.Job;
import entities.NickName;
import entities.Person;
import interfaces.IPersonFacade;
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
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public synchronized PersonDTO addPerson(PersonDTO personDTO) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        if (personDTO.getName() == null) {
            throw new WebApplicationException("Name is missing", 400);
        } else if (personDTO.getJob() == null || personDTO.getJob().getTitle() == null) {
            throw new WebApplicationException("Job title is missing.", 400);
        } else if (personDTO.getNickName() == null || personDTO.getNickName().getNickName() == null) {
            throw new WebApplicationException("Nickname is missing.", 400);
        } else if (personDTO.getHobbies().isEmpty() || personDTO.getHobbies() == null) {
            throw new WebApplicationException("Hobbies are missing.", 400);
        }
        try {
            em.getTransaction().begin();

            Person person = new Person(personDTO.getName());

            //Create or use existing Job
            if (personDTO.getJob() != null) {
                Job job = findJobInDB(personDTO.getJob());
                if (job.getId() > 0) {
                    job = em.find(Job.class, job.getId());
                }
                job.addPerson(person);
                em.persist(job);
            }

            //Create or use existing NickName
            if (personDTO.getNickName() != null) {
                NickName nickName = findNickNameInDB(personDTO.getNickName());
                if (nickName.getId() > 0) {
                    nickName = em.find(NickName.class, nickName.getId());
                }
                System.out.println(nickName);
                em.persist(nickName);
                person.setNickName(nickName);
                em.merge(person);
                System.out.println(nickName);
            }

            //Create or use existing Hobby
            if (personDTO.getHobbies() != null) {
                Hobby hobby;
                for (HobbyDTO hobbyDTO : personDTO.getHobbies()) {
                    hobby = findHobbyInDB(hobbyDTO);
                    if (hobby.getId() > 0) {
                        hobby = em.find(Hobby.class, hobby.getId());
                    }
                    System.out.println(hobby);
                    person.addHobby(hobby);
                }
//                em.persist(person);
            }
            em.persist(person);

            em.getTransaction().commit();
            return new PersonDTO(person);
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO deletePerson(int id) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            em.remove(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new WebApplicationException("Could not delete, provided id: " + id + " does not exist", 404);
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO getPerson(int id) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        try {
            Person person = em.find(Person.class, id);
            return new PersonDTO(person);
        } catch (NullPointerException ex) {
            throw new WebApplicationException("No Person with provided id: " + id, 404);
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getAllPersons() throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT person FROM Person person", Person.class);
            List<Person> persons = query.getResultList();
//            System.out.println(persons.size());
            ArrayList<PersonDTO> personDTOs = new ArrayList<>();
            for(Person person : persons){
                personDTOs.add(new PersonDTO(person));
            }
            return personDTOs;
        } catch (RuntimeException ex) {
            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO personDTO) throws WebApplicationException {
        if (personDTO.getName() == null) {
            throw new WebApplicationException("Name is missing", 400);
        } else if (personDTO.getJob() == null || personDTO.getJob().getTitle() == null) {
            throw new WebApplicationException("Job title is missing.", 400);
        } else if (personDTO.getNickName() == null || personDTO.getNickName().getNickName() == null) {
            throw new WebApplicationException("Nickname is missing.", 400);
        } else if (personDTO.getHobbies().isEmpty() || personDTO.getHobbies() == null) {
            throw new WebApplicationException("Hobbies are missing.", 400);
        }
        EntityManager em = emf.createEntityManager();
        Person personEditInfo = new Person(personDTO.getName());
        personEditInfo.setJob(new Job(personDTO.getJob().getTitle()));
        personEditInfo.setNickName(new NickName(personDTO.getNickName().getNickName()));
        personEditInfo.setHobbies(personDTO.makeHobbylist());

        Person oldPerson = em.find(Person.class, personDTO.getId());

        try {
            em.getTransaction().begin();

            oldPerson.setName(personDTO.getName());

            //Create or use existing Job
            if (personDTO.getJob() != null && !oldPerson.getJob().equals(personEditInfo.getJob())) {
                Job job = findJobInDB(personDTO.getJob());
                if (job.getId() > 0) {
                    job = em.find(Job.class, job.getId());
                }
                job.addPerson(oldPerson);
                em.persist(job);
            }

            //Create or use existing NickName
            if (personDTO.getNickName() != null && !oldPerson.getNickName().equals(personEditInfo.getNickName())) {
                NickName nickName = findNickNameInDB(personDTO.getNickName());
                if (nickName.getId() > 0) {
                    nickName = em.find(NickName.class, nickName.getId());
                }
                System.out.println(nickName);
                em.persist(nickName);
                oldPerson.setNickName(nickName);
//                em.merge(oldPerson);
                System.out.println(nickName);
            }

            //Create or use existing Hobby
            if (personDTO.getHobbies() != null) {

                for (int i = 0; i < personDTO.makeHobbylist().size(); i++) {
                    Hobby hobby;
                    int count = 0;
                    for (int j = 0; j < oldPerson.getHobbies().size(); j++) {
                        if (personDTO.makeHobbylist().get(i).equals(oldPerson.getHobbies().get(j))) {
                            count = 1;
                        }
                    }
                    if (count == 0) {
                        hobby = findHobbyInDB(new HobbyDTO(personDTO.makeHobbylist().get(i)));
                        if (hobby.getId() > 0) {
                            hobby = em.find(Hobby.class, hobby.getId());
                        }
                        oldPerson.addHobby(hobby);
//                        em.merge(oldPerson);
                    }
                }
            }
            em.persist(oldPerson);
            em.getTransaction().commit();
            return new PersonDTO(oldPerson);
        } //        catch (RuntimeException ex) {
        //            throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
        //        } 
        finally {
            em.close();
        }
    }

    private Job findJobInDB(JobDTO jobDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT j FROM Job j WHERE j.title = :title ", Job.class);
            query.setParameter("title", jobDTO.getTitle());
            Job job = (Job) query.getSingleResult();
            return job;
        } catch (NoResultException ex) {
            Job job = new Job(jobDTO.getTitle());
            return job;
        } finally {
            em.close();
        }
    }

    private NickName findNickNameInDB(NickNameDTO nickNameDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT n FROM NickName n WHERE n.name = :name ", NickName.class);
            query.setParameter("name", nickNameDTO.getNickName());
            NickName nickName = (NickName) query.getSingleResult();
            return nickName;
        } catch (NoResultException ex) {
            //System.out.println("Her");
            NickName nickName = new NickName(nickNameDTO.getNickName());
//            System.out.println(nickName);
            return nickName;
        } finally {
            em.close();
        }
    }

    private Hobby findHobbyInDB(HobbyDTO hobbyDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :name ", Hobby.class);
            query.setParameter("name", hobbyDTO.getName());
            Hobby hobby = (Hobby) query.getSingleResult();
            return hobby;
        } catch (NoResultException ex) {
            //System.out.println("Her");
            Hobby hobby = new Hobby(hobbyDTO.getName());
            return hobby;
        } finally {
            em.close();
        }
    }

}
