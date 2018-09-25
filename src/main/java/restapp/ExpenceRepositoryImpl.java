package restapp;

import model.Expence;
import model.ExpenceSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class ExpenceRepositoryImpl implements ExpenceRepositoryCustom{
    private final MongoOperations operations ;


    @Autowired
    public ExpenceRepositoryImpl(MongoOperations operations ) {
        this.operations = operations;
    }


    @Override
    public List<ExpenceSummary> getReportsFor(){
        AggregationResults<ExpenceSummary> results = operations.aggregate(newAggregation(Expence.class, //
                 match(where("type").is("Grocery")), //
                // unwind("items"), //
                //    project("id", "customerId", "items") //
                //           .andExpression("'$items.price' * '$items.quantity'").as("lineTotal"), //
                group("type")  //
                        .addToSet("type").as("type")//, //
                        .sum("amount").as("totalAmount") //
                        .count().as("count")
                //  project("id", "items", "netAmount") //
                //          .and("orderId").previousOperation() //
                //         .andExpression("netAmount * [0]", 1).as("taxAmount") //
                //        .andExpression("netAmount * (1 + [0])", 1).as("totalAmount") //
        ), Expence.class,ExpenceSummary.class);
        System.out.println("results"+results.getMappedResults());
        return results.getMappedResults();
    }
}
