package com.emc.data.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageObjectRepository extends
		JpaRepository<MessageObject, String> {

}
