package net.rorarius.repository;

import net.rorarius.data.UserToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends MongoRepository<UserToken, Integer> {

    UserToken findById(ObjectId userId);

    UserToken findByToken(String token);

}
