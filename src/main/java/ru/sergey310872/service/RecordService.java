package ru.sergey310872.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergey310872.repository.RecordRepository;
import ru.sergey310872.entity.Record;
import ru.sergey310872.entity.RecordStatus;
import ru.sergey310872.entity.dto.RecordsContainerDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecordService {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Transactional(readOnly = true)
    public RecordsContainerDto findAllRecords(String filterMode) {
//        List<Record> records = recordRepository.findAllRecords();
        List<Record> records = recordRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        int numberOfDoneRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.DONE).count();
        int numberOfActiveRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.ACTIVE).count();


        if (filterMode == null || filterMode.isBlank()) {
            return new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);
        }

        String filterModeInUpperCase = filterMode.toUpperCase();
        List<String> allowedFilterModes = Arrays.stream(RecordStatus.values()).map(Enum::name).collect(Collectors.toList());
        if (allowedFilterModes.contains(filterModeInUpperCase)) {
            List<Record> filteredRecords = records.stream()
                    .filter(r->r.getStatus() == RecordStatus.valueOf(filterModeInUpperCase))
                    .collect(Collectors.toList());
            return new RecordsContainerDto(filteredRecords, numberOfDoneRecords, numberOfActiveRecords);
        }else {
            return new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);
        }
    }

    public void saveRecord(String title) {
        if (title != null && !title.isBlank()) {
            recordRepository.save(new Record(title));
        }
    }

    public void updateRecordStatus(int id, RecordStatus newStatus) {
//        Pair<Integer, String>
        recordRepository.update(id, newStatus);
//        throw new RuntimeException("Тестовое исключение.");

//        recordRepository.findById(id).ifPresent(record -> {
//            record.setStatus(newStatus);
//            recordRepository.save(record);
//        });

//        recordRepository.updateRecordStatus(id, newStatus);
    }

    public void deleteRecord(int id) {
        recordRepository.deleteById(id);
    }
}
