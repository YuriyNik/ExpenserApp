package restapp;

import model.Expence;
import model.ExpenceSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class ExpenceRepositoryImpl implements ExpenceRepositoryCustom {
    private static final Logger logger = LogManager.getLogger(ExpenceRepositoryImpl.class);

    private final MongoOperations operations ;

    @Autowired
    public ExpenceRepositoryImpl(MongoOperations operations ) {
        this.operations = operations;
    }


    @Override
    public List<ExpenceSummary> getReportsForYearAndType(int year, String type) {

      //  String datestr = new String("2015-11-20 17:45:19");
      //  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      //  LocalDateTime dateFrom = LocalDateTime.parse(datestr, formatter);
     //   LocalDateTime dateTo = LocalDateTime.now();
     //   int year=2016;
     //   int month=1;
        AggregationResults<ExpenceSummary> results = operations.aggregate(newAggregation(Expence.class, //
                 match(where("owner").is("admin")
                 .andOperator(where("type").is(type)))
              //   .andOperator(where("date").lte(LocalDateTime.now())
                      //.andOperator(where("year(date)").is(year)
                //     .andOperator(where("date").regex("2016")
                //    .andOperator(where("owner").is("admin")))
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
                match(where("year").is(year)
                         //.andOperator(where("month").is(month)
                         )
                        //   .andOperator(where("date").lte(LocalDateTime.now())
                        //.andOperator(where("year(date)").is(year)

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
        logger.info("getReportsForYearAndType results.size="+results.getMappedResults().size());
        return results.getMappedResults();
    }
}
