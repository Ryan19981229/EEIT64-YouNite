package tw.com.younite.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;
import tw.com.younite.entity.InterestEntity;
import tw.com.younite.entity.UserProfileEntity;

import java.util.*;

@Component
public class DataTransferUtil {

    private static final double EARTH_RADIUS = 6371.0;

    // 計算年齡
    public int calculateAge(Date birthday) {
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(birthday);
        Calendar currentDate = Calendar.getInstance();

        int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        if (currentDate.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH) ||
                (currentDate.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) &&
                        currentDate.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }
        return age;
    }

    // 解析興趣表格
    public List<String> parseInterests(String interestString) {
        String[] interests = interestString.replaceAll("[\\[\\]]", "").split(", ");
        return Arrays.asList(interests);
    }

    public List<String> parseInterestEntities(List<InterestEntity> entities) {
        List<String> results = new ArrayList<>();
        for (InterestEntity entity: entities) {
            results.add(entity.getInterest());
        }
        return results;
    }

    public double parseDistance(UserProfileEntity currentUser, UserProfileEntity comparedUser) {
        double currentLat = 0.0;
        double currentLng = 0.0;
        double comparedLat = 0.0;
        double comparedLng = 0.0;
        double distance = 0.0;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(currentUser.getLocation());
            JsonNode jsonNode2 = mapper.readTree(comparedUser.getLocation());
            currentLat = jsonNode.get("lat").asDouble();
            currentLng = jsonNode.get("lng").asDouble();
            comparedLat = jsonNode2.get("lat").asDouble();
            comparedLng = jsonNode2.get("lng").asDouble();
        } catch (Exception  e) {
            return 394;
        }
        distance = calculateHaversineDistance(currentLat, currentLng, comparedLat, comparedLng);
        return distance;
    }

    public static double calculateHaversineDistance(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS * c;
        return Math.round(distance * 10.0) / 10.0;
    }

}
