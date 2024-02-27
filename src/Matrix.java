import java.util.Arrays;
import java.util.Scanner;
public class Matrix implements Cloneable {
    private int x,y;
    private double[][] data;
    public void setData(double[][] data) {
        this.data = data;
    }
    void printMatrix(){
        for (int i = 0; i < this.x; i++){
            for (int j = 0; j < this.y; j++) {
                System.out.print(this.data[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Tạo ma trận x cột y hàng có tất cả các giá trị = 0
     * @param x số hàng
     * @param y số cột
     */
    public Matrix(int x, int y){
        this.x = x;
        this.y = y;
        data = new double[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                data[i][j] = 0;
            }
        }
    }

    /**
     * Hàm nhập giá trị cho mảng
     */
    public void init(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập vào ma trận: ");
        for (int i = 0; i < this.x; i++){
            for (int j = 0; j < this.y; j++) {
                System.out.print("Phần tử dòng " + (i + 1) + " cột " + (j + 1) + ": ");
                data[i][j] = sc.nextInt();
            }
        }
    }

    /**
     * Hàm tính tổng ma trận hiện tại với một ma trận khác
     * @param matrix ma trận số hạng
     * @return ma trận kết quả
     */
    public Matrix plus(Matrix matrix){
        if(this.x != matrix.x || this.y != matrix.y){
            throw new ArithmeticException("Không cùng kích thước, không thể cộng 2 ma trận này");
        }
        Matrix result = new Matrix(x,y);
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                result.data[i][j] = this.data[i][j] + matrix.data[i][j];
            }
        }
        return result;
    }
    /**
     * Hàm tính hiệu ma trận hiện tại với một ma trận khác
     * @param matrix ma trận số bị trừ
     * @return ma trận kết quả
     */
    public Matrix minus(Matrix matrix){
        if(this.x != matrix.x || this.y != matrix.y){
            throw new ArithmeticException("Không cùng kích thước, không thể trừ 2 ma trận này");
        }
        Matrix result = new Matrix(x,y);
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                result.data[i][j] = this.data[i][j] - matrix.data[i][j];
            }
        }
        return result;
    }
    /**
     * Hàm tính tích ma trận hiện tại với một ma trận khác
     * @param matrix ma trận số nhân
     * @return ma trận kết quả
     */
    public Matrix multiply(Matrix matrix){
        if(this.y != matrix.x){
            throw new ArithmeticException("Không thể nhân 2 ma trận này");
        }
        Matrix result = new Matrix(this.x, matrix.y);
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < matrix.y; j++) {
                double sum = 0;
                for (int k = 0; k < this.y; k++) {
                    sum += this.data[i][k] * matrix.data[k][j];
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    /**
     * Hàm sắp xếp lại các hàng của ma trận, hàng nào có nhiều số 0 hơn
     * tính từ vị trí đầu tiên tới phần tử gần nhất khác 0 thì xuống dưới
     * @return ma trận sau khi đã sắp xếp
     */
    public Matrix sortByZero(){
        Matrix matrix = this.clone();
        int[] zeroCount = new int[this.x];
        Arrays.fill(zeroCount, 0);
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                if (matrix.data[i][j] == 0){
                    zeroCount[i]++;
                }
                else {
                    break;
                }
            }
        }
        for (int i = 0; i < this.x; i++) {
            for (int j = i + 1; j < this.x; j++) {
                if (zeroCount[i] > zeroCount[j]){
                    double[] temp = matrix.data[i];
                    matrix.data[i] = matrix.data[j];
                    matrix.data[j] = temp;
                    int tempCount = zeroCount[i];
                    zeroCount[i] = zeroCount[j];
                    zeroCount[j] = tempCount;
                }
            }
        }
        return matrix;
    }

    /**
     * Chuyển ma trận về dạng tam giác trên
     * @return ma trận sau khi đã về dạng tam giác trên
     */
    public Matrix toTriangularForm() {
        Matrix result = this.sortByZero();
        int rowCount = this.x;
        int colCount = this.y;
        for (int k = 0; k < rowCount; k++) {
            double pivot = 0;
            int colIndex = 0;
            for (int i = 0; i < colCount; i++) {
                if (result.data[k][i] != 0){
                    pivot = result.data[k][i];
                    colIndex = i;
                    break;
                }
            }
            for (int i = k + 1; i < rowCount; i++) {
                double ratio = result.data[i][colIndex] / pivot;
                for (int j = colIndex; j < colCount; j++) {
                    result.data[i][j] -= ratio * result.data[k][j];
                }
            }
        }
        return result;
    }

    /**
     * Tính định thức của ma trận
     * @return giá trị định thức
     */
    public double det(){
        if (this.x != this.y){
            throw new ArithmeticException("Không phải ma trận vuông, không có định thức");
        }
        Matrix matrix = this.toTriangularForm();
        double det = 1;
        for (int i = 0; i < this.x; i++) {
            det *= matrix.data[i][i];
        }
        return det;
    }

    @Override
    public Matrix clone() {
        Matrix matrix = new Matrix(this.x, this.y);
        matrix.data = new double[this.x][this.y];
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                matrix.data[i][j] = this.data[i][j];
            }
        }
        return matrix;
    }
}
