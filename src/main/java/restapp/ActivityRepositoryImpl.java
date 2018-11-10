package restapp;

import model.Activity;
import model.ActivitySummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class ActivityRepositoryImpl implements ActivityRepositoryCustom{

    private static final Logger logger = LogManager.getLogger(ActivityRepositoryImpl.class);

    private final MongoOperations operations ;

    @Autowired
    public ActivityRepositoryImpl(MongoOperations operations ) {
        this.operations = operations;
    }

    @Override
    public List<ActivitySummary> getActivitySummaryForYear(String owner, int year) {

        AggregationResults<ActivitySummary> results = operations.aggregate(newAggregation(Activity.class,
                match(where("owner").is(owner)
                 //       .andOperator(where("type").is(type))
                ),

                project()
                        .andExpression("type").as("type")
                        .andExpression("year(date)").as("year")
                       // .andExpression("month(date)").as("month")
                        .andExpression("distance").as("distance")
                ,
                match(where("year").is(year))
                ,
                group("type","year")
                        .sum("distance").as("totalDistance") //
                        .count().as("count")
        ), Activity.class,ActivitySummary.class);
        logger.info("getActivitySummaryForYear results.size="+results.getMappedResults().size());
        return results.getMappedResults();
    }

    @Override
    public List<ActivitySummary> getActivitySummaryForYearByMonth(String owner, int year) {

        AggregationResults<ActivitySummary> results = operations.aggregate(newAggregation(Activity.class,
                match(where("owner").is(owner)
                        //       .andOperator(where("type").is(type))
                ),

                project()
                        .andExpression("type").as("type")
                        .andExpression("year(date)").as("year")
                        .andExpression("month(date)").as("month")
                        .andExpression("distance").as("distance")
                ,
                match(where("year").is(year))
                ,
                group("type","year","month")
                        .sum("distance").as("totalDistance") //
                        .count().as("count")
        ), Activity.class,ActivitySummary.class);
        logger.info("getActivitySummaryForYearByMonth results.size="+results.getMappedResults().size());
        return results.getMappedResults();
    }

    @Override
    public List<ActivitySummary> getActivitySummaryAll(String owner) {

        AggregationResults<ActivitySummary> results = operations.aggregate(newAggregation(Activity.class,
                match(where("owner").is(owner)
                        //       .andOperator(where("type").is(type))
                ),

                project()
                        .andExpression("type").as("type")
                        .andExpression("year(date)").as("year")
                     //   .andExpression("month(date)").as("month")
                        .andExpression("distance").as("distance")
                //,
               // match()//where("year").is(year))
                ,
                group("type","year")
                        .sum("distance").as("totalDistance") //
                        .count().as("count")
        ), Activity.class,ActivitySummary.class);
        logger.info("getActivitySummaryAll results.size="+results.getMappedResults().size());
        return results.getMappedResults();
    }


}
