package com.helmes.worker_reg.controller;

import com.helmes.worker_reg.entities.Sector;
import com.helmes.worker_reg.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

import javax.persistence.Persistence;
import javax.persistence.Query;

@RestController
public class SectorController {

    private final SectorRepository sectorRepository;

    @Autowired
    public SectorController(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    /**
     * Recursively poll all sectors depending on depth.
     *
     * @param depth of sector hierarchy required.
     * @return list of objects based on depth, containing sector name,
     * sector id and depth.
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/sector/{depth}")
    public List getRecursiveSectorRoot(@PathVariable int depth) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory
                ("sectorPersistence");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createNativeQuery("WITH recursive recursive_query AS " +
                "(SELECT sc.sector_name, h.child_id, 1 AS depth FROM sector sc " +
                "JOIN sector_hierarchy h ON sc.id = h.child_id WHERE h.parent_id IS NULL UNION ALL " +
                "SELECT sc.sector_name, h.child_id, depth + 1 FROM sector sc " +
                "JOIN sector_hierarchy h ON sc.id = h.child_id " +
                "JOIN recursive_query r ON h.parent_id = r.child_id) " +
                "SELECT * FROM recursive_query WHERE depth = ?1");

        q.setParameter(1, depth);

        List sectorsWithDepth = q.getResultList();

        em.getTransaction().commit();
        em.close();

        return sectorsWithDepth;
    }

    /**
     * Recursively poll all sectors depending on depth and parent.
     * Which means that polled sectors will be based on depth and their parent.
     *
     * @param depth of sector hierarchy required.
     * @param parentId of sector list we want to get.
     * @return list of objects based on depth, containing sector name,
     * sector id, depth and most importantly parent.
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/sector/{depth}/{parentId}")
    public List getRecursiveSectorBranches(@PathVariable int depth, @PathVariable int parentId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory
                ("sectorPersistence");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createNativeQuery("WITH recursive recursive_query AS " +
                "(SELECT sc.sector_name, h.child_id, h.parent_id, 1 AS depth FROM sector sc " +
                "JOIN sector_hierarchy h ON sc.id = h.child_id WHERE h.parent_id IS NULL UNION ALL " +
                "SELECT sc.sector_name, h.child_id, h.parent_id, depth + 1 FROM sector sc " +
                "JOIN sector_hierarchy h ON sc.id = h.child_id " +
                "JOIN recursive_query r ON h.parent_id = r.child_id) " +
                "SELECT * FROM recursive_query WHERE depth = ?1 AND parent_id = ?2");

        q.setParameter(1, depth);
        q.setParameter(2, parentId);

        List sectorsWithDepth = q.getResultList();

        em.getTransaction().commit();
        em.close();

        return sectorsWithDepth;
    }
    /**
     * Find all sectors.
     * Useful for finding id using name and vice-versa.
     *
     * @return all sectors(id, name)
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/sector")
    public List<Sector> getAllSectorsIdAndNames() {
       return sectorRepository.findAll();
    }
//    /**
//     * Find sector by id.
//     *
//     * @return sector(id, name)
//     */
//    @CrossOrigin(origins = "http://localhost:8080")
//    @GetMapping("/sector/{id}")
//    public Optional<Sector> getAllSectorsById(@PathVariable Long id) {
//       return sectorRepository.findById(id);
//    }


}
