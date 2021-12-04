// Вычисляем с помощью приведения матрицы к треугольному виду
class Decompose{
    public double triangle(double[][] m) {
        // Сведение к нулю елмента m10 (столбец 1, строка 0)
        // Коэффициент k равен частному от обратного первого элемента второй строки
        // Умножаем первую строку на коэффициент k и прибавляем ко второй строке
        double k = (-m[1][0]) / m[0][0];
        m[1][0] = m[0][0] * k + m[1][0]; // Данный элемент равен нулю
        m[1][1] = m[0][1] * k + m[1][1];
        m[1][2] = m[0][2] * k + m[1][2];

        // Сведение к нулю елмента m20 (столбец 2, строка 0)
        // Коэффициент k равен частному от обратного первого элемента третей строки
        // Умножаем первую строку на коэффициент k и прибавляем к третей строке
        k = (-m[2][0]) / m[0][0];
        m[2][0] = m[0][0] * k + m[2][0]; // Данный элемент равен нулю
        m[2][1] = m[0][1] * k + m[2][1];
        m[2][2] = m[0][2] * k + m[2][2];

        // Сведение к нулю елмента m21 (столбец 2, строка 1)
        k = (-m[2][1]) / m[1][1];
        m[2][1] = m[1][1] * k + m[2][1];
        m[2][2] = m[1][2] * k + m[2][2];

        return m[0][0]*m[1][1]*m[2][2];
    }
}


// Вычисляем с помощью детерминанта (правила «треугольников»)
class Determinant{
    public double determinant(double[][] m) {
        return  m[0][0]*m[1][1]*m[2][2] + m[0][1]*m[1][2]*m[2][0] + m[0][2]*m[1][0]*m[2][1] -
                m[0][2]*m[1][1]*m[2][0] - m[0][0]*m[1][2]*m[2][1] - m[0][1]*m[1][0]*m[2][2];
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { //тест написанного:
        double[][] test = new double[3][3];

        //совершенно произвольная матрица
        test[0][0]=1;
        test[0][1]=2;
        test[0][2]=3;

        test[1][0]=4;
        test[1][1]=5;
        test[1][2]=7;

        test[2][0]=8;
        test[2][1]=3;
        test[2][2]=0;

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
        // Класс Determinant
        Determinant dt = new Determinant();
        Result = dt.determinant(test);
        System.out.print("Результат Класса Determinant: ");
        System.out.println(Result);
        // Класс Determinant
        System.out.print("Потраченное время: ");
        System.out.println((System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        // Класс Decompose
        Decompose dc = new Decompose();
        Result = dc.triangle(test);
        System.out.print("Результат Класса Decompose: ");
        System.out.println(Result);
        // Класс Decompose
        System.out.print("Потраченное время: ");
        System.out.println((System.currentTimeMillis() - time));
    }
}