package com.helmes.worker_reg.repository;

import com.helmes.worker_reg.entities.Sector;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SectorRepository extends JpaRepository<Sector, Long> {


    //@Query(value ="with recursive recursive_query as (select sc.sector_name, h.child_id, 1 as
    // depth from sector sc join sector_hierarchy h on sc.id = h.child_id where h.parent_id is
    // null union all select sc.sector_name, h.child_id, depth + 1 from sector sc join sector_hierarchy
    // h on sc.id = h.child_id join recursive_query r on h.parent_id = r.child_id) select * from recursive_query where depth = 4",
    // nativeQuery = true)
    //List<Sector> findRecursive(int depth);

}
