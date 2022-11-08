package com.transcribe.aws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transcribe.aws.model.LogsModel;

@Repository
public interface LogsRepository  extends JpaRepository<LogsModel, Long>{

}
