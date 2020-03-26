package com.yamalc.ytmp.grpc.client;


import com.yamalc.ytmp.grpc.thermo.BodyTemperatureResponse;
import com.yamalc.ytmp.grpc.thermo.ThermoGrpc;
import com.yamalc.ytmp.grpc.thermo.UserIdRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ThermoApiClient {
    Logger logger = Logger.getLogger(getClass().getName());

    ManagedChannel channel;
    ThermoGrpc.ThermoBlockingStub blockingStub;

    public static ThermoApiClient create(String host, int port) {
        ThermoApiClient simpleClient = new ThermoApiClient();
        simpleClient.channel =
                ManagedChannelBuilder
                        .forAddress(host, port)
                        .usePlaintext()
                        .build();
        simpleClient.blockingStub =
                ThermoGrpc.newBlockingStub(simpleClient.channel);

        return simpleClient;
    }

    public double getLatestThermoInfo(String id) {
        UserIdRequest request =
                UserIdRequest
                        .newBuilder()
                        .setId(id)
                        .build();

        try {
            BodyTemperatureResponse response = blockingStub.latestBodyTemperature(request);

            logger.info(String.format("response: result = %f", response.getBodyTemperature()));

            return response.getBodyTemperature();
        } catch (StatusRuntimeException e) {
            Status status = Status.fromThrowable(e);
            logger.info("error: status code = " + status.getCode() + ", description = " + status.getDescription());
            e.printStackTrace();
            throw e;
        }
    }

    public double getLatestHealthCheck(String id) {
        UserIdRequest request =
                UserIdRequest
                        .newBuilder()
                        .setId(id)
                        .build();

        try {
            BodyTemperatureResponse response = blockingStub.latestHealthCheck(request);

            logger.info(String.format("response: result = %f", response.getBodyTemperature()));

            return response.getBodyTemperature();
        } catch (StatusRuntimeException e) {
            Status status = Status.fromThrowable(e);
            logger.info("error: status code = " + status.getCode() + ", description = " + status.getDescription());
            e.printStackTrace();
            throw e;
        }
    }

    public double getRecentlyHealthCheck(String id) {
        UserIdRequest request =
                UserIdRequest
                        .newBuilder()
                        .setId(id)
                        .build();

        try {
            BodyTemperatureResponse response = blockingStub.recentlyHealthCheck(request);

            logger.info(String.format("response: result = %f", response.getBodyTemperature()));

            return response.getBodyTemperature();
        } catch (StatusRuntimeException e) {
            Status status = Status.fromThrowable(e);
            logger.info("error: status code = " + status.getCode() + ", description = " + status.getDescription());
            e.printStackTrace();
            throw e;
        }
    }

    public double getNormalBodyTemperature(String id) {
        UserIdRequest request =
                UserIdRequest
                        .newBuilder()
                        .setId(id)
                        .build();

        try {
            BodyTemperatureResponse response = blockingStub.normalBodyTemperature(request);

            logger.info(String.format("response: result = %f", response.getBodyTemperature()));

            return response.getBodyTemperature();
        } catch (StatusRuntimeException e) {
            Status status = Status.fromThrowable(e);
            logger.info("error: status code = " + status.getCode() + ", description = " + status.getDescription());
            e.printStackTrace();
            throw e;
        }
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5L, TimeUnit.SECONDS);
    }
}
