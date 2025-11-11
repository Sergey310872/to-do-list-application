package ru.sergey310872.repository;

import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sergey310872.entity.Record;
import ru.sergey310872.entity.RecordStatus;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
    @Modifying
    @Query("update Record set status = :status where id = :id")
    void update(int id, @Param("status") RecordStatus  newStatus);

    List<Record> findAllByStatus(RecordStatus status);

//    public List<Record> findAllRecords() {
//        Query query = entityManager.createQuery("select r from Record r order by r.id");
//        List<Record> records = query.getResultList();
//
//        return records;
//    }
//
//    public void saveRecord(Record record) {
//        entityManager.persist(record);
//    }
//
//    public void updateRecordStatus(int id, RecordStatus newStatus) {
//        Query query = entityManager.createQuery("update Record set status = :status where id = :id");
//        query.setParameter("id", id);
//        query.setParameter("status", newStatus);
//        query.executeUpdate();
//    }
//
//    public void deleteRecord(int id) {
//            Query query = entityManager.createQuery("delete from Record where id = :id");
//            query.setParameter("id", id);
//            query.executeUpdate();
//    }
}
