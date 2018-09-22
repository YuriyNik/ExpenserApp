
myApp.controller('Report', ['$scope','$http', function($scope,$http) {

        console.log('Report started');


db.expence.aggregate(
   [ {
       $match :
        {$and:
    [
    {type : "Grocery" }
    ,
   // {'$expr': { '$eq': [{ '$month': '$date' }, 1 ] }}
    //,
       {'$expr': { '$eq': [{ '$year': '$date' }, 2016 ] }}

   //{date: {'$gte': ISODate("2013-01-01T00:00:00.0Z"), '$lt': ISODate("2015-02-01T00:00:00.0Z")}}

     ]
    }

   } ,
   {
       $group:
         {
           _id: {type:"$type", month: { $month: "$date"},
               year: { $year: "$date" }
           },
           totalAmount: { $sum:  "$amount" },
           count: { $sum: 1 }
         }
     }
   ]
)



    }]);
