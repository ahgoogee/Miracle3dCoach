package api;

public class PythonApiTest {
    public static void main(String[] args) {
        PythonAPI.startCoach();

        for (int i = 0; i < 10; i++) {
            System.out.println("VVVVVVVVVVVVVVVV");
            float result = PythonAPI.getBenchmarkResult();
            System.out.println("Avg Fit:"+result);
            System.out.println("AAAAAAAAAAAAAAAA");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        PythonAPI.stopCoach();
    }
}
