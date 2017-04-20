package net.rorarius.repository;

import net.rorarius.data.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

    User findById(ObjectId userId);

    Long countByEmail(String email);

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
