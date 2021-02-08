package com.flawlessconcepts.roadlink2

import android.content.Context
import android.view.View
import android.widget.Toast
import com.flawlessconcepts.roadlink.roadlink.RetrofitService
import com.flawlessconcepts.roadlink.roadlinkNetwork.CustomerItem
import com.flawlessconcepts.roadlink.roadlinkNetwork.LocationMatrix
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Help {
    fun getTripDistanceForEnuguNorth(
        locationMatrix: LocationMatrix,
        destination: String,
        hasReturn: String
    ): Double {
        var distance: Double = 0.0

        if (destination.equals("Enugu-North", true)) {
            if (hasReturn.equals("True", true)) {
                return 30.0
            } else {
                return 20.0
            }
        } else {
            distance += 9
            if (hasReturn.equals("true", true)) {
                distance += (locationMatrix.distance.toDouble() * 2.0)
                return distance
            } else {
                distance += locationMatrix.distance.toString().toDouble()
                return distance
            }
        }
    }


    fun getTripDistanceForOthers(
        enuguNorthmartrix: LocationMatrix, othersMatrix: LocationMatrix,
        destination: String, hasReturn: String
    ): Double {
        var distance: Double = 0.0

        if (hasReturn.equals("True", true)) {
            enuguNorthmartrix.distance.toDouble()
            distance += (othersMatrix.distance.toDouble() * 2.0)
            return distance
        } else {
            enuguNorthmartrix.distance.toDouble()
            distance += othersMatrix.distance.toDouble()
            return distance
        }
    }


fun getCalculatedCostForEnuguNorth(
    locationMatrix: LocationMatrix, destination: String,
    hasReturn: String, numPassengers: Int
): Double {
    var totalTime: Double = 0.0
    var cost: Double = 0.0

    if (destination.equals("Enugu-North", true)) {
        if (hasReturn.equals("true", true)) {
            totalTime = 40.0
            cost = (12.5 * totalTime * numPassengers!!.toString().toDouble())
            return cost
        } else {
            totalTime = 25.0

            cost = (15.0 * totalTime * numPassengers!!.toString().toDouble())
            return cost

        }
    } else {
        if (hasReturn.equals("true", true)) {
            totalTime += 11
            totalTime += (locationMatrix.time.toDouble() * 2)
            cost = (12.5 * totalTime * numPassengers!!.toString().toDouble())
            return cost
        } else {
            totalTime += 11
            totalTime += locationMatrix.time.toDouble()
            cost = (12.5 * totalTime * numPassengers!!.toString().toDouble())
            return cost
        }
    }
}

fun getCalculatedCostForOthers(
    enuguNorthmartrix: LocationMatrix,
    otherMatrix: LocationMatrix,
    destination: String, hasReturn: String, numPassengers: Int
): Double {
    var totalTime: Double = 0.0
    var cost: Double = 0.0


    if (hasReturn.equals("true", true)) {
        totalTime += enuguNorthmartrix.time.toDouble()
        totalTime += (otherMatrix.time.toDouble() * 2)

        cost = (12.5 * totalTime * numPassengers.toString().toInt())
        return cost
    } else {
        totalTime += enuguNorthmartrix.time.toDouble()
        totalTime += otherMatrix.time.toDouble()

        cost = (12.5 * totalTime * numPassengers.toString().toInt())
        return cost

    }
}


fun capacity(numberPassengers: Int): Double {
    when (numberPassengers) {
        in 1..3 -> return 3.0
        in 4..7 -> return 7.0
        in 8..12 -> return 12.0
        else -> return 0.0
    }
}

}