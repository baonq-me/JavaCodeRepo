public class Main
{
    public static void main(String[] args) {
        Stopwatch st = new Stopwatch();
        st.start();

        for (long x = 0; x <= 1000000000L; x++)
        {

        }

        st.stop();
        System.out.println(st.toString());
    }
}