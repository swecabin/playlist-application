package com.cloud.thealphaproject.thealpha;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository  extends CrudRepository<TestEntity, Integer> {

    public TestEntity findById(int id);
}
