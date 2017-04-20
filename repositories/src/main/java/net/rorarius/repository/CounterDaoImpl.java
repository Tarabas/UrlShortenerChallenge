package net.rorarius.repository;

import net.rorarius.data.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class CounterDaoImpl implements CounterDao {

    @Autowired
    private MongoOperations mongoOperation;

    @Override
    public int getNextCount(String key) {
        Query query = new Query(Criteria.where("_id").is(key));

        Update update = new Update();
        update.inc("counter", 1);

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        Counter counter = mongoOperation.findAndModify(query, update, options, Counter.class);

        return Math.toIntExact(counter.getCounter());
    }
}
