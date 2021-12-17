import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Random;

// Вычисляем с помощью приведения матрицы к треугольному виду
class Triangel {

    private double[][] matrix;
    private int sign = 1;

    public Triangel(double[][] test) {
        this.matrix = test;
    }

    public int getSign() {
        return sign;
    }

    public BigDecimal determinant() {

        BigDecimal deter;
        if (isUpperTriangular() || isLowerTriangular())
            deter = multiplyDiameter().multiply(BigDecimal.valueOf(sign));

        else {
            makeTriangular();
            deter = multiplyDiameter().multiply(BigDecimal.valueOf(sign));

        }
        return deter;
    }


    /*  receives a matrix and make it triangular using allowed operations
        on columns and rows
    */
    public void makeTriangular() {

        for (int j = 0; j < matrix.length; j++) {
            sortCol(j);
            for (int i = matrix.length - 1; i > j; i--) {
                if (matrix[i][j] == 0)
                    continue;

                double x = matrix[i][j];
                double y = matrix[i - 1][j];
                multiplyRow(i, (-y / x));
                addRow(i, i - 1);
                multiplyRow(i, (-x / y));
            }
        }
    }


    public boolean isUpperTriangular() {

        if (matrix.length < 2)
            return false;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix[i][j] != 0)
                    return false;

            }

        }
        return true;
    }


    public boolean isLowerTriangular() {

        if (matrix.length < 2)
            return false;

        for (int j = 0; j < matrix.length; j++) {
            for (int i = 0; j > i; i++) {
                if (matrix[i][j] != 0)
                    return false;

            }

        }
        return true;
    }


    public BigDecimal multiplyDiameter() {

        BigDecimal result = BigDecimal.ONE;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i == j)
                    result = result.multiply(BigDecimal.valueOf(matrix[i][j]));

            }

        }
        return result;
    }


    // when matrix[i][j] = 0 it makes it's value non-zero
    public void makeNonZero(int rowPos, int colPos) {

        int len = matrix.length;

        outer:
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (matrix[i][j] != 0) {
                    if (i == rowPos) { // found "!= 0" in it's own row, so cols must be added
                        addCol(colPos, j);
                        break outer;

                    }
                    if (j == colPos) { // found "!= 0" in it's own col, so rows must be added
                        addRow(rowPos, i);
                        break outer;
                    }
                }
            }
        }
    }


    //add row1 to row2 and stores int row1
    public void addRow(int row1, int row2) {

        for (int j = 0; j < matrix.length; j++)
            matrix[row1][j] += matrix[row2][j];
    }


    //adds col1 to col2
    public void addCol(int col1, int col2) {

        for (int i = 0; i < matrix.length; i++)
            matrix[i][col1] += matrix[i][col2];
    }


    //multiply the whole row by num
    public void multiplyRow(int row, double num) {

        if (num < 0)
            sign *= -1;


        for (int j = 0; j < matrix.length; j++) {
            matrix[row][j] *= num;
        }
    }


    //multiply the whole column by num
    public void multiplyCol(int col, double num) {

        if (num < 0)
            sign *= -1;

        for (int i = 0; i < matrix.length; i++)
            matrix[i][col] *= num;

    }


    // sort the cols from the biggest to the lowest value
    public void sortCol(int col) {

        for (int i = matrix.length - 1; i >= col; i--) {
            for (int k = matrix.length - 1; k >= col; k--) {
                double tmp1 = matrix[i][col];
                double tmp2 = matrix[k][col];

                if (Math.abs(tmp1) < Math.abs(tmp2))
                    replaceRow(i, k);
            }
        }
    }


    //replace row1 with row2
    public void replaceRow(int row1, int row2) {

        if (row1 != row2)
            sign *= -1;

        double[] tempRow = new double[matrix.length];

        for (int j = 0; j < matrix.length; j++) {
            tempRow[j] = matrix[row1][j];
            matrix[row1][j] = matrix[row2][j];
            matrix[row2][j] = tempRow[j];
        }
    }


    //replace col1 with col2
    public void replaceCol(int col1, int col2) {

        if (col1 != col2)
            sign *= -1;

        System.out.printf("replace col%d with col%d, sign = %d%n", col1, col2, sign);
        double[][] tempCol = new double[matrix.length][1];

        for (int i = 0; i < matrix.length; i++) {
            tempCol[i][0] = matrix[i][col1];
            matrix[i][col1] = matrix[i][col2];
            matrix[i][col2] = tempCol[i][0];
        }
    } }




// Java программа для поиска детерминанта
// матрица

class GFG {


    // Размер входной квадратной матрицы

    int N;


    // Функция для получения кофактора

    // mat [p] [q] in temp [] []. н это

    // текущее измерение мата [] []

    static void getCofactor(double[][] mat,

                            double[][] temp, double p, double q, int n) {

        int i = 0, j = 0;


        // Цикл для каждого элемента
        // матрица
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                // Копирование во временную матрицу
                // только те элементы, которые
                // не в данной строке и столбце
                if (row != p && col != q) {
                    temp[i][j++] = mat[row][col];

                    // Строка заполнена, поэтому увеличивается
                    // индекс строки и сброс кол
                    //показатель
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    double determinantOfMatrix(double[][] mat, int n) {
        this.N = n;
        float D = 0; // Инициализировать результат

        // Базовый случай: если матрица содержит один
        // элемент
        if (n == 1)
            return mat[0][0];

        // Для хранения кофакторов
        double temp[][] = new double[N][N];

        // Сохранить множитель знака
        int sign = 1;

        // Итерация для каждого элемента первой строки
        for (int f = 0; f < n; f++) {

            // Получение Cofactor of mat [0] [f]
            getCofactor(mat, temp, 0, f, n);
            D += sign * mat[0][f] * determinantOfMatrix(temp, n - 1);

            // условия должны быть добавлены с альтернативным знаком
            sign = -sign;

        }
        return D;
    }
}


public class MatrixCalculation {

    //рекурсивная функция - вычисляет значение определителя.
    // Если на входе определитель 2х2 - просто вычисляем (крест-на-крест), иначе раскладываем на миноры.
    // Для каждого минора вычисляем ЕГО определитель, рекурсивно вызывая ту же функцию..
    public double CalculateMatrix(double[][] matrix){
        double calcResult=0.0;
        if (matrix.length==2){
            calcResult=matrix[0][0]*matrix[1][1]-matrix[1][0]*matrix[0][1];
        }
        else{
            int koeff=1;
            for(int i=0; i<matrix.length; i++){
                if(i%2==1){  //я решила не возводить в степень, а просто поставить условие - это быстрее.
                    // Т.к. я раскладываю всегда по первой строке, то фактически я проверяю на четность значение i+0.
                    koeff=-1;
                }
                else{
                    koeff=1;
                };
                //Разложение:
                calcResult += koeff*matrix[0][i]*this.CalculateMatrix(this.GetMinor(matrix,0,i));
            }
        }

        //воВозвращаем ответ
        return calcResult;
    }

    //функция, к-я возвращает нужный нам минор.
    // На входе - определитель, из к-го надо достать минор и номера строк-столбцов, к-е надо вычеркнуть.
    private double[][] GetMinor(double[][] matrix, int row, int column){
        int minorLength = matrix.length-1;
        double[][] minor = new double[minorLength][minorLength];
        int dI=0;//эти переменные для того, чтобы "пропускать" ненужные нам строку и столбец
        int dJ=0;
        for(int i=0; i<=minorLength; i++){
            dJ=0;
            for(int j=0; j<=minorLength; j++){
                if(i==row){
                    dI=1;
                }
                else{
                    if(j==column){
                        dJ=1;
                    }
                    else{
                        minor[i-dI][j-dJ] = matrix[i][j];
                    }
                }
            }
        }

        return minor;

    }


    public static void main(String[] args) { //тест написанного:

        // Инициализируем генератор
        Random rnd = new Random(System.currentTimeMillis());
        // Получаем случайное число в диапазоне от min до max (включительно)
        int min = -10;
        int max = 10;
        int n = Integer.parseInt(args[0]);
        double[][] test = new double[n][n];
        // Заполняем матрицу
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                test[i][j] = min + rnd.nextInt(max - min + 1);
            }
        }

        //совершенно произвольная матрица
        double[][] test2 = test.clone();

        long time = System.currentTimeMillis();
        // Класс MatrixCalculation
        MatrixCalculation mc = new MatrixCalculation();
        double Result = mc.CalculateMatrix(test);
        System.out.print("Результат Класса MatrixCalculation: ");
        System.out.println(Result);
        // Класс MatrixCalculation
        System.out.print("Потраченное время: ");
        System.out.println((System.currentTimeMillis() - time));


        time = System.currentTimeMillis();
        // Класс Triangel
        Triangel deter = new Triangel(test);
        BigDecimal det = deter.determinant();
        String Result2 = NumberFormat.getInstance().format(det);
        System.out.print("Результат Класса Triangel: ");
        System.out.println(Result2);
        // Класс Triangel
        System.out.print("Потраченное время: ");
        System.out.println((System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        // Класс GFG
        GFG gfg = new GFG();
        Result = gfg.determinantOfMatrix(test2, n);
        System.out.print("Результат Класса GFG: ");
        System.out.println(Result);
        // Класс GFG
        System.out.print("Потраченное время: ");
        System.out.println((System.currentTimeMillis() - time));
    }
}