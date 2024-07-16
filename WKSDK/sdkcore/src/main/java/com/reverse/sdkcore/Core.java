package com.reverse.sdkcore;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mavsdk.MavsdkEventQueue;
import io.mavsdk.System;
import io.mavsdk.mavsdkserver.MavsdkServer;
import io.reactivex.disposables.Disposable;

public class Core {
    public static final String BACKEND_IP_ADDRESS = "127.0.0.1";
    private MavsdkServer mavsdkServer = new MavsdkServer();
    private System drone;
    private final List<Disposable> disposables = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Core.class);
    private boolean isMavsdkServerRunning = false;

    public void runMavsdkServer() {
        MavsdkEventQueue.executor().execute(() -> {
            int mavsdkServerPort = mavsdkServer.run();
            drone = new System(BACKEND_IP_ADDRESS, mavsdkServerPort);

            disposables.add(drone.getTelemetry().getFlightMode().distinctUntilChanged()
                    .subscribe(flightMode -> logger.debug("flight mode: " + flightMode)));
            disposables.add(drone.getTelemetry().getArmed().distinctUntilChanged()
                    .subscribe(armed -> logger.debug("armed: " + armed)));
            disposables.add(drone.getTelemetry().getPosition().subscribe(position -> {
                java.lang.System.out.println("Latitude:"+ position.getLatitudeDeg().toString() + " ,Longitude:" + position.getLongitudeDeg().toString());
            }));

            isMavsdkServerRunning = true;
            java.lang.System.out.println("mavsdk_server started!");
        });
    }

    public void destroyMavsdkServer() {
        MavsdkEventQueue.executor().execute(() -> {
            for (Disposable disposable : disposables) {
                disposable.dispose();
            }
            disposables.clear();
            drone.dispose();
            drone = null;
            mavsdkServer.stop();
            mavsdkServer.destroy();

            isMavsdkServerRunning = false;
            java.lang.System.out.println("mavsdk_server stopped!");
        });
    }

    public void Arm(){
        drone.getAction().arm().subscribe();
    }

    public void DisArm(){
        drone.getAction().disarm().subscribe();
    }

    public void TakeOff(){
        drone.getAction().takeoff().subscribe();
    }

    public void Land(){
        drone.getAction().land().subscribe();
    }
}
