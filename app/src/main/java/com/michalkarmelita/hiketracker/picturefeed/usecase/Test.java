package com.michalkarmelita.hiketracker.picturefeed.usecase;

import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Predicate;

/**
 * Created by MK on 10/07/2017.
 */

public class Test {

    public Test(RxLocation rxLocation, LocationRequest locationRequest) {

//        Observable<Location> locationObservable = rxLocation.location()
//                .updates(locationRequest);
//
//        Observable<Boolean> distanceSample = locationObservable
//                .scan(new BiFunction<Location, Location, Integer>() {
//                    @Override
//                    public Integer apply(Location location, Location location2) throws Exception {
//                        return (int) location.distanceTo(location2);
//                    }
//                })
//                .filter(new Predicate<Location>() {
//                    @Override
//                    public boolean test(Location location) throws Exception {
//                        return ;
//                    }
//                })
//
//        locationObservable.sample()
//                .scan(new BiFunction<Location, Location, Integer>() {
//                    @Override
//                    public Integer apply(Location location, Location location2) throws Exception {
//                        return (int) location.distanceTo(location2);
//                    }
//                })
//                .filter()

    }
}
