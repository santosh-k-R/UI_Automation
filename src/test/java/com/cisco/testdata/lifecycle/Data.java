package com.cisco.testdata.lifecycle;

import com.cisco.utils.auth.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.cisco.utils.LifeCycleUtils.*;
import static com.cisco.utils.auth.TokenGenerator.getToken;

public class Data {

    public static List<String> users = Arrays.asList("MACHINE", "superAdminL2", "superAdminL1", "adminL1", "adminL2", "standardUserL1", "standardUserL2");

    static {
        System.out.println("Generating Token for the users...");
        users.forEach(user -> {
            System.out.println("Generating token for the user.." + user);
            getToken(new User(user));
        });
    }

    public static final String CUSTOMER_ID_ZHEHUI = "G5P8lsEowYcKNZ";
    public static final String CUSTOMER_ID_GRAPHIC = "yrxqBHmPkNhZ60";
    public static final String CUSTOMER_ID_NISA = "VP8vMi47jAsGj0";
    public static final String BUID_ZHEHUI = "962738";
    public static final String SOLUTION = "IBN";
    public static final String DEFAULT_USER_ROLE = "superAdminL2";
    public static final String STANDARD_USER_ROLE = "standardUserL2";

    public static final String SUCCESS_TRACK = "Campus Network";
    public static final String CX_CLOUD_ACCOUNT_L2 = "ZHEJIANG ZHENGYUAN ZHEHUI SCIENCE TECHNOLOGY COLTD";
    public static final String CX_CLOUD_ACCOUNT_L1 = "SACYR SA";
    public static final String MULTIPLE_BUID_CLOUD_ACCOUNT = "GRAPHIC PACKAGING HOLDING COMPANY";
    public static final String PARTNER_CLOUD_ACCOUNT = "GRAPHIC PACKAGING HOLDING COMPANY";

    public static final String ACC_URL = "lifecycle/racetrack/v2/acc/";
    public static final String BOOKMARK_URL = "lifecycle/racetrack/v2/bookmarks";
    public static final String FEEDBACK_URL = "commonsvc/feedback/v2/cxportal/";
    public static final String RACETRACK_URL = "lifecycle/racetrack/v2/pitstop/info";
    public static final String ATX_URL = "lifecycle/racetrack/v2/atx";
    public static final String COMMUNITY_URL = "lifecycle/racetrack/v2/communities/threads/";
    public static final String SUCCESS_TIPS_URL = "lifecycle/racetrack/v2/successPaths/";
    public static final String ELEARNING_URL = "lifecycle/racetrack/v2/elearning/";

    //*********************************RaceTrack Data********************************************************************
//    public static final RaceTrackPoJo ZHEJIANG_RACETRACK_DATA = getRaceTrackResponse(CUSTOMER_ID_ZHEHUI, DEFAULT_USER_ROLE);
//    public static final RaceTrackPoJo GRAPHIC_RACETRACK_DATA = getRaceTrackResponse(CUSTOMER_ID_GRAPHIC, DEFAULT_USER_ROLE);

    //*************************************************************Elearning**********************************************************
    /*public static final List<ElearningPoJo> ELEARNING_DATA_LIST_CISCO = getElearningDataFromAllUseCase(CUSTOMER_ID_ZHEHUI, SOLUTION, DEFAULT_USER_ROLE);

    //******************************************************Success Tips******************************************************
    public static final List<SuccessTipsPoJo> SUCCESS_DATA_LIST_CISCO = getSuccessTipsDataFromAllUseCase(CUSTOMER_ID_ZHEHUI, SOLUTION, DEFAULT_USER_ROLE);

    //****************************************************Community*****************************************************
    public static final List<CommunityListPoJo> COMMUNITY_DATA_LIST_CISCO = getCommunityDataFromAllUseCase(CUSTOMER_ID_ZHEHUI, SOLUTION, DEFAULT_USER_ROLE);*/

    public static List<CommunityListPoJo> getCommunityDataFromAllUseCase(String customerId, String solution, String userRole) {
        RaceTrackPoJo raceTrackPoJo = getRaceTrackResponse(customerId, userRole);
        List<CommunityListPoJo> communityListPoJos = new ArrayList<>();
        Map<String, String> currentPitStops = getUserCurrentPitStopForAllUseCase(customerId, userRole);
        for (String useCase : currentPitStops.keySet()) {
            String pitStop = currentPitStops.get(useCase);
            if (!pitStop.equalsIgnoreCase("Purchase")) {
                CommunityListPoJo communityListPoJoPrivate = getCommunityResponse(customerId, solution, useCase, currentPitStops.get(useCase), userRole, "public ");
                communityListPoJoPrivate.setCustomerId(customerId);
                communityListPoJoPrivate.setBuId(raceTrackPoJo.getBuId());
                communityListPoJoPrivate.setCavId(raceTrackPoJo.getCavId());
                communityListPoJos.add(communityListPoJoPrivate);

                CommunityListPoJo communityListPoJoPublic = getCommunityResponse(customerId, solution, useCase, currentPitStops.get(useCase), userRole, "public");
                communityListPoJoPublic.setCustomerId(customerId);
                communityListPoJoPublic.setBuId(raceTrackPoJo.getBuId());
                communityListPoJoPublic.setCavId(raceTrackPoJo.getCavId());
                communityListPoJos.add(communityListPoJoPublic);
            }
        }
        return communityListPoJos;
    }

    public static CommunityListPoJo getCommunityListData(List<CommunityListPoJo> communityListPoJos, Predicate<CommunityListPoJo> predicate) {
        CommunityListPoJo dataPOJO = new CommunityListPoJo();

        for (CommunityListPoJo successTipsPoJo : communityListPoJos) {
            List<CommunityListPoJo> collectItems = communityListPoJos.stream()
                    .filter(predicate)
                    .collect(Collectors.toList());

            if (collectItems.size() > 0) {
                dataPOJO.setPitstop(successTipsPoJo.getPitstop());
                dataPOJO.setSolution(successTipsPoJo.getSolution());
                dataPOJO.setUsecase(successTipsPoJo.getUsecase());
                dataPOJO.setCavId(successTipsPoJo.getCavId());
                dataPOJO.setBuId(successTipsPoJo.getBuId());
                dataPOJO.setCustomerId(successTipsPoJo.getCustomerId());
                return dataPOJO;
            }
        }
        return dataPOJO;
    }

    public static List<SuccessTipsPoJo> getSuccessTipsDataFromAllUseCase(String customerId, String solution, String userRole) {
        RaceTrackPoJo raceTrackPoJo = getRaceTrackResponse(customerId, userRole);
        List<SuccessTipsPoJo> successTipsPoJos = new ArrayList<>();
        Map<String, String> currentPitStops = getUserCurrentPitStopForAllUseCase(customerId, userRole);
        for (String useCase : currentPitStops.keySet()) {
            String pitStop = currentPitStops.get(useCase);
            if (!pitStop.equalsIgnoreCase("Purchase")) {
                SuccessTipsPoJo successTipsPoJo = getSuccessTipsResponse(customerId, solution, useCase, currentPitStops.get(useCase), userRole);
                successTipsPoJo.setCustomerId(customerId);
                successTipsPoJo.setBuId(raceTrackPoJo.getBuId());
                successTipsPoJo.setCavId(raceTrackPoJo.getCavId());
                successTipsPoJos.add(successTipsPoJo);
            }
        }
        return successTipsPoJos;

    }

    public static SuccessTipsPoJo getSuccessTipsData(List<SuccessTipsPoJo> successTipsPoJos, Predicate<SuccessTipsPoJo.ItemsItem> predicate) {
        SuccessTipsPoJo dataPOJO = new SuccessTipsPoJo();

        for (SuccessTipsPoJo successTipsPoJo : successTipsPoJos) {
            List<SuccessTipsPoJo.ItemsItem> collectItems = successTipsPoJo.getItems().stream()
                    .filter(predicate)
                    .collect(Collectors.toList());

            if (collectItems.size() > 0) {
                dataPOJO.setPitstop(successTipsPoJo.getPitstop());
                dataPOJO.setSolution(successTipsPoJo.getSolution());
                dataPOJO.setUsecase(successTipsPoJo.getUsecase());
                dataPOJO.setItems(collectItems);
                dataPOJO.setCavId(successTipsPoJo.getCavId());
                dataPOJO.setBuId(successTipsPoJo.getBuId());
                dataPOJO.setCustomerId(successTipsPoJo.getCustomerId());
                dataPOJO.getItems().forEach(itemsItem -> {
                    itemsItem.setCustomerId(successTipsPoJo.getCustomerId());
                    itemsItem.setSolution(successTipsPoJo.getSolution());
                    itemsItem.setUsecase(successTipsPoJo.getUsecase());
                    itemsItem.setPitstop(successTipsPoJo.getPitstop());
                    itemsItem.setBuId(successTipsPoJo.getBuId());
                    itemsItem.setCavId(successTipsPoJo.getCavId());
                });
                return dataPOJO;
            }
        }
        return dataPOJO;
    }

    public static List<ElearningPoJo> getElearningDataFromAllUseCase(String customerId, String solution, String userRole) {
        RaceTrackPoJo raceTrackPoJo = getRaceTrackResponse(customerId, userRole);
        List<ElearningPoJo> elearningPojos = new ArrayList<>();
        Map<String, String> currentPitStops = getUserCurrentPitStopForAllUseCase(customerId, userRole);
        for (String useCase : currentPitStops.keySet()) {
            String pitStop = currentPitStops.get(useCase);
            if (!pitStop.equalsIgnoreCase("Purchase")) {
                ElearningPoJo elearningPojo = getElearningResponse(customerId, solution, useCase, currentPitStops.get(useCase), userRole);
                elearningPojo.setCustomerId(customerId);
                elearningPojo.setBuId(raceTrackPoJo.getBuId());
                elearningPojo.setCavId(raceTrackPoJo.getCavId());
                elearningPojos.add(elearningPojo);
            }
        }
        return elearningPojos;
    }

    public static ElearningPoJo getElearningData(List<ElearningPoJo> elearningPoJos, Predicate<ElearningPoJo.ItemsItem> predicate) {
        ElearningPoJo dataPOJO = new ElearningPoJo();

        for (ElearningPoJo elearningPoJo : elearningPoJos) {
            List<ElearningPoJo.ItemsItem> collectItems = elearningPoJo.getItems().stream()
                    .filter(predicate)
                    .collect(Collectors.toList());

            if (collectItems.size() > 0) {
                dataPOJO.setPitstop(elearningPoJo.getPitstop());
                dataPOJO.setSolution(elearningPoJo.getSolution());
                dataPOJO.setUsecase(elearningPoJo.getUsecase());
                dataPOJO.setItems(collectItems);
                dataPOJO.setCavId(elearningPoJo.getCavId());
                dataPOJO.setBuId(elearningPoJo.getBuId());
                dataPOJO.setCustomerId(elearningPoJo.getCustomerId());
                dataPOJO.getItems().forEach(itemsItem -> {
                    itemsItem.setCustomerId(elearningPoJo.getCustomerId());
                    itemsItem.setSolution(elearningPoJo.getSolution());
                    itemsItem.setUsecase(elearningPoJo.getUsecase());
                    itemsItem.setPitstop(elearningPoJo.getPitstop());
                    itemsItem.setBuId(elearningPoJo.getBuId());
                    itemsItem.setCavId(elearningPoJo.getCavId());
                });
                return dataPOJO;
            }
        }
        return dataPOJO;
    }

    public static List<ATXPoJo> getATXDataFromAllUseCase(String customerId, String solution, String userRole) {
        RaceTrackPoJo raceTrackPoJo = getRaceTrackResponse(customerId, userRole);
        List<ATXPoJo> atxPoJos = new ArrayList<>();
        Map<String, String> currentPitStops = getUserCurrentPitStopForAllUseCase(customerId, userRole);
        for (String useCase : currentPitStops.keySet()) {
            String pitStop = currentPitStops.get(useCase);
            if (!pitStop.equalsIgnoreCase("Purchase")) {
                ATXPoJo atxPoJo = getATXResponse(customerId, solution, useCase, currentPitStops.get(useCase), userRole, "");
                atxPoJo.setCustomerId(customerId);
                atxPoJo.setBuId(raceTrackPoJo.getBuId());
                atxPoJo.setCavId(raceTrackPoJo.getCavId());
            }
        }

        Map<String, String> nextPitStops = getUserNextPitStopForAllUseCase(currentPitStops);
        for (String useCase : nextPitStops.keySet()) {
            String pitStop = nextPitStops.get(useCase);
            if (!pitStop.equalsIgnoreCase("Purchase")) {
                ATXPoJo atxPoJo = getATXResponse(customerId, solution, useCase, nextPitStops.get(useCase), userRole,"");
                atxPoJo.setCustomerId(customerId);
                atxPoJo.setBuId(raceTrackPoJo.getBuId());
                atxPoJo.setCavId(raceTrackPoJo.getCavId());
                atxPoJos.add(atxPoJo);
            }
        }
        System.out.println("ATX PoJo Size: "+atxPoJos.size());
        return atxPoJos;
    }

    public static ATXPoJo getATXData(List<ATXPoJo> atxPoJos, Predicate<ATXPoJo.ItemsItem> predicate) {
        ATXPoJo dataPOJO = new ATXPoJo();

        for (ATXPoJo atxs : atxPoJos) {
            List<ATXPoJo.ItemsItem> collectItems = atxs.getItems().stream()
                    .filter(predicate)
                    .collect(Collectors.toList());

            if (collectItems.size() > 0) {
                dataPOJO.setPitstop(atxs.getPitstop());
                dataPOJO.setSolution(atxs.getSolution());
                dataPOJO.setUsecase(atxs.getUsecase());
                dataPOJO.setItems(collectItems);
                dataPOJO.setCavId(atxs.getCavId());
                dataPOJO.setBuId(atxs.getBuId());
                dataPOJO.setCustomerId(atxs.getCustomerId());
                dataPOJO.getItems().forEach(itemsItem -> {
                    itemsItem.setCustomerId(atxs.getCustomerId());
                    itemsItem.setSolution(atxs.getSolution());
                    itemsItem.setUsecase(atxs.getUsecase());
                    itemsItem.setPitstop(atxs.getPitstop());
                    itemsItem.setBuId(atxs.getBuId());
                    itemsItem.setCavId(atxs.getCavId());
                });
                return dataPOJO;
            }
        }
        return dataPOJO;
    }

    public static List<ACCPoJo> getACCDataFromAllUseCase(String customerId, String solution, String userRole) {
        RaceTrackPoJo raceTrackPoJo = getRaceTrackResponse(customerId, userRole);
        List<ACCPoJo> accPoJos = new ArrayList<>();

        Map<String, String> currentPitStops = getUserCurrentPitStopForAllUseCase(customerId, userRole);
        for (String useCase : currentPitStops.keySet()) {
            String pitStop = currentPitStops.get(useCase);
            if (!pitStop.equalsIgnoreCase("Purchase")) {
                ACCPoJo accPojo = getACCResponse(customerId, solution, useCase, currentPitStops.get(useCase), userRole);
                accPojo.setCustomerId(customerId);
                accPojo.setBuId(raceTrackPoJo.getBuId());
                accPojo.setCavId(raceTrackPoJo.getCavId());
                accPoJos.add(accPojo);
            }
        }

        Map<String, String> nextPitStops = getUserNextPitStopForAllUseCase(currentPitStops);
        for (String useCase : nextPitStops.keySet()) {
            String pitStop = nextPitStops.get(useCase);
            if (!pitStop.equalsIgnoreCase("Purchase")) {
                ACCPoJo accPojo = getACCResponse(customerId, solution, useCase, nextPitStops.get(useCase), userRole);
                accPojo.setCustomerId(customerId);
                accPojo.setBuId(raceTrackPoJo.getBuId());
                accPojo.setCavId(raceTrackPoJo.getCavId());
                accPoJos.add(accPojo);
            }
        }
        return accPoJos;
    }

    public static ACCPoJo getACCData(List<ACCPoJo> accPojos, Predicate<ACCPoJo.ItemsItem> predicate) {
        ACCPoJo dataPOJO = new ACCPoJo();

        for (ACCPoJo accs : accPojos) {
            List<ACCPoJo.ItemsItem> collectItems = accs.getItems().stream()
                    .filter(predicate)
                    .collect(Collectors.toList());

            if (collectItems.size() > 0) {
                dataPOJO.setPitstop(accs.getPitstop());
                dataPOJO.setSolution(accs.getSolution());
                dataPOJO.setUsecase(accs.getUsecase());
                dataPOJO.setItems(collectItems);
                dataPOJO.setCavId(accs.getCavId());
                dataPOJO.setBuId(accs.getBuId());
                dataPOJO.setCustomerId(accs.getCustomerId());
                dataPOJO.getItems().forEach(itemsItem -> {
                    itemsItem.setCustomerId(accs.getCustomerId());
                    itemsItem.setSolution(accs.getSolution());
                    itemsItem.setUsecase(accs.getUsecase());
                    itemsItem.setPitstop(accs.getPitstop());
                    itemsItem.setBuId(accs.getBuId());
                    itemsItem.setCavId(accs.getCavId());
                });
                return dataPOJO;
            }
        }
        return dataPOJO;
    }

}
