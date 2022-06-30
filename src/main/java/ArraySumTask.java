import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ArraySumTask extends RecursiveTask<Integer> {

    private int[] array;

    public ArraySumTask(int[] array) {
        this.array = array;
    }


    @Override
    protected Integer compute() {
        if (array.length <= 2) {
            return Arrays.stream(array).sum();
        }
        ArraySumTask firstHalfArrayValueSumCounter = new ArraySumTask(Arrays.copyOfRange(array, 0, array.length / 2));
        ArraySumTask secondHalfArrayValueSumCounter = new ArraySumTask(Arrays.copyOfRange(array, array.length / 2, array.length));
        firstHalfArrayValueSumCounter.fork();
        secondHalfArrayValueSumCounter.fork();
        return firstHalfArrayValueSumCounter.join() + secondHalfArrayValueSumCounter.join();
    }
}

    /*@Override
    protected Integer compute(){
        final int diff = end - start;
        switch(diff){
            case0: return 0;
            case1: return array[start];
            case2: return array[start]+ array[start+1];
            default: return forkTasksAndGetResult();
        }
    }*/

   /* private int forkTasksAndGetResult(){
        final int middle =(end - start)/2+ start;
        // Создаем задачу для левой части диапазона
        ArraySumTask task1 =new ArraySumTask(start,middle, array);
        // Создаем задачу для правой части диапазона
        ArraySumTask task2 =new ArraySumTask(middle, end, array);
        // Запускаем обе задачи в пуле
        invokeAll(task1, task2);
        // Суммируем результаты выполнения обоих задач
        return task1.join()+ task2.join();
}*/
