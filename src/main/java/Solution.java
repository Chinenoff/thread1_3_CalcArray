import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Solution {

    public static final int MAXRANDOM = 1000000; //диапазон случайных чисел в массиве 0 - 1000
    public static final int SIZEARRAY = 500;  //размерность массива

    public static void main(String[] args) {
        int[] myArray = arrayGenerator(); //генерация случайного массива

        //тестовый вывод
        //System.out.println(" \n Исходный массив \n " + Arrays.toString(myArray));

        //подсчет перебором через цикл FOR
        int resultSumFor = sumArrayFor(myArray);
        long timeFor = timer(() -> sumArrayFor(myArray));
        System.out.println("\n Подсчёт перебором через цикл FOR");
        System.out.printf(" Затраченное время: %d наносекунд. Сумма значений элементов массива : " +
                        "%d \n ",
                timeFor, resultSumFor);


        //подсчет перебором через цикл stream
        int resultSumStream = sumArrayStream(myArray);
        long timeStream = timer(() -> sumArrayStream(myArray));
        System.out.println("\n Подсчёт перебором через Stream");
        System.out.printf(" Затраченное время: %d наносекунд. Сумма значений элементов массива : " +
                        "%d \n ",
                timeStream, resultSumStream);


        //подсчет рекурсией в нескольких потоках
        int resultSumThread = sumArrayThread(myArray);
        long timeThread = timer(() -> sumArrayThread(myArray));
        System.out.println("\n Подсчёт многопоточной рекурсией ForkJoinPool");
        System.out.printf(" Затраченное время: %d наносекунд. Сумма значений элементов массива : " +
                        "%d \n ",
                timeThread, resultSumThread);
    }

    // метод генерации массива случайных целочисленных чисел от 0 до MAXRANDOM
    static int[] arrayGenerator() {
        int[] array = new int[Solution.SIZEARRAY];
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(MAXRANDOM);
        }
        return array;
    }

    // метод подсчёта времени выполнения методов
    private static long timer(Runnable method) {
        long time = System.nanoTime();
        method.run();
        time = System.nanoTime() - time;
        return TimeUnit.NANOSECONDS.convert(time, TimeUnit.NANOSECONDS);
    }

    //метод суммирования массива через FOR
    static int sumArrayFor(int[] targetArray) {
        int result = 0;
        for (int j : targetArray) {
            result += j;
        }
        return result;
    }

    //метод суммирования массива через Stream
    static int sumArrayStream(int[] targetArray) {
        return IntStream.of(targetArray).sum();
    }

    //метод суммирования массива через ForkJoinPool
    static int sumArrayThread(int[] targetArray) {
        ArraySumTask counter = new ArraySumTask(targetArray);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(counter);
    }
}
