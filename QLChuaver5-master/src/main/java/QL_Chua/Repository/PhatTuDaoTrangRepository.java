package QL_Chua.Repository;

import QL_Chua.Models.PhatTuDaoTrangs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhatTuDaoTrangRepository extends JpaRepository<PhatTuDaoTrangs, Integer> {
}
