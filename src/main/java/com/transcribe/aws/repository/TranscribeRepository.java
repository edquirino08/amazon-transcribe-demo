package com.transcribe.aws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transcribe.aws.model.TranscribeModel;

@Repository
public interface TranscribeRepository extends JpaRepository<TranscribeModel, Long> {

}
