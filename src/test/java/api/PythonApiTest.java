package api;

import com.miracle3d.coach.api.API;

public class PythonApiTest {
    public static void main(String[] args) {
        API.startCoach();

        for (int i = 0; i < 10; i++) {
            System.out.println("VVVVVVVVVVVVVVVV");
            float result = API.getBenchmarkResult();
            System.out.println("Avg Fit:"+result);
            System.out.println("AAAAAAAAAAAAAAAA");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        API.stopCoach();
    }
}
