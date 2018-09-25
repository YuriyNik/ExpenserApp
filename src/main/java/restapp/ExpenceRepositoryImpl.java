package restapp;

import model.Expence;
import model.ExpenceSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        String datestr = new String("2015-11-20 17:45:19");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(datestr, formatter);
        int year=2016;
        int month=1;
        AggregationResults<ExpenceSummary> results = operations.aggregate(newAggregation(Expence.class, //
                 match(where("type").is("Grocery")
               //  .andOperator(where("date").gte(dateTime)
              //   .andOperator(where("date").lte(LocalDateTime.now())
                      //.andOperator(where("year(date)").is(year)
                     .andOperator(where("date").is(month)
                     .andOperator(where("owner").is("admin"))))
                , //
                // unwind("items"), //
                //    project("id", "customerId", "items") //
                //           .andExpression("'$items.price' * '$items.quantity'").as("lineTotal"), //
                project()
                        .andExpression("type").as("type")
                        .andExpression("year(date)").as("year")
                        .andExpression("month(date)").as("month")
                        .andExpression("amount").as("amount")
                ,
                group("type","year","month")

                     //   .addToSet("type").as("type")//, //
                     //   .addToSet("year").as("year")//, //
                     //   .addToSet("month").as("month")//, //
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
