package com.transcribe.aws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transcribe.aws.model.ErrorModel;

@Repository
public interface ErrorRepository extends JpaRepository<ErrorModel, Long> {

}
