package second_lab;

class Matrix {
    protected double[][] matrix;

    Matrix() {
        matrix = new double[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                matrix[i][j] = 0;
    }

    Matrix(double[][] values) {
        this.matrix = values;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setValue(int i, int j, double value) {
        this.matrix[i][j] = value;
    }

    public double getValue(int i, int j){
        return this.matrix[i][j];
    }

    public void printMatrix(){
        System.out.println("MATRIX : ");
        for (int i = 0; i < 3; i++)
                System.out.println(String.format("%-10f %-10f %-10f", matrix[i][0], matrix[i][1],matrix[i][2]));
        System.out.println();
    }

    public static Matrix getIdentityMatrix() {
        Matrix res = new Matrix();
        for (int i = 0; i < 4; i++) {
            res.matrix[i][i] = 1;
        }
        return res;
    }

    public static Matrix getTranslateMatrix(double tx, double ty, double tz) {
        Matrix res = getIdentityMatrix();
        res.setValue(3, 0, tx);
        res.setValue(3, 1, ty);
        res.setValue(3, 2, tz);

        return res;
    }

    public static Matrix getScalingMatrix(double sx, double sy, double sz){
        Matrix res = new Matrix();
        res.setValue(0, 0 , sx);
        res.setValue(1, 1, sy);
        res.setValue(2, 2, sz);
        res.setValue(3, 3, 1);

        return res;
    }

    public Vertex multiplyMatrixToVector(double x, double y, double z){
        double[] res = multiplyMatrixToVector(new double[]{x, y, z, 1});
        return new Vertex(res[0]/res[3], res[1]/res[3], res[2]/res[3]);
    }

    private double[] multiplyMatrixToVector(double[] vector){
        double[] res = new double[]{0,0,0,0};
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                res[i] += vector[j] * matrix[j][i];

        return res;
    }

    public Matrix multiplyMatrixToMatrix(Matrix m) {
        Matrix res = new Matrix();

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++)
                    res.matrix[i][j] += this.matrix[i][k] * m.matrix[k][j];

        return res;
    }

    private static Matrix rotate(double angle, int i, int j){
        angle = Math.toRadians(angle);
        Matrix res = getIdentityMatrix();
        double val = Math.cos(angle);

        res.setValue(i, i, val);
        res.setValue(j, j, val);
        res.setValue(i, j, Math.sin(angle));
        res.setValue(j, i, - res.getValue(i, j));

        return res;
    }

    public static Matrix rotateMatrixX(double angle){
        return rotate(angle, 1, 2);
    }
    public static Matrix rotateMatrixY(double angle){
        return rotate(angle, 2, 0);
    }
    public static Matrix rotateMatrixZ(double angle){
        return rotate(angle, 0, 1);
    }

    public static Matrix getProjectionMatrix(int i, double k){
        Matrix m = getIdentityMatrix();
        m.matrix[3][3] = 0;
        m.matrix[i][3] = k;

        return m;
    }

}