package QL_Chua.Repository;

import QL_Chua.Models.Chuas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChuaRepository extends JpaRepository<Chuas, Integer> {
}
