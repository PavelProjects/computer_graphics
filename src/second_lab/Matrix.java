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

    public Matrix getIdentityMatrix() {
        Matrix res = new Matrix();
        for (int i = 0; i < 4; i++) {
            res.matrix[i][i] = 1;
        }
        return res;
    }

    public Matrix getTranslateMatrix(double tx, double ty, double tz) {
        Matrix res = getIdentityMatrix();
        res.setValue(3, 0, tx);
        res.setValue(3, 1, ty);
        res.setValue(3, 2, tz);

        return res;
    }

    public Matrix getScalingMatrix(double sx, double sy, double sz){
        Matrix res = new Matrix();
        res.setValue(0, 0 , sx);
        res.setValue(1, 1, sy);
        res.setValue(2, 2, sz);
        res.setValue(3, 3, 1);

        return res;
    }

    public double[] multiplyMatrixToVector(double[] vector) throws Exception {
        if (vector.length != 4)
            throw new Exception("Wrong vector length");
        double[] res = new double[4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                res[i] += vector[i] * matrix[i][j];

        return res;
    }

    public Matrix multiplyMatrixToMatrix(Matrix m) {
        Matrix res = new Matrix();

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++)
                    res.matrix[i][j] += this.matrix[i][k] * m.matrix[k][i];

        return res;
    }

    private Matrix rotate(double angle, int i, int j){
        Matrix res = getIdentityMatrix();
        double val = Math.cos(angle);

        res.setValue(i, i, val);
        res.setValue(j, j, val);
        res.setValue(i, j, Math.sin(angle));
        res.setValue(j, i, - res.getValue(i, j));

        return res;
    }

    public Matrix rotateMatrixX(double angle){
        return rotate(angle, 1, 2);
    }
    public Matrix rotateMatrixY(double angle){
        return rotate(angle, 2, 0);
    }
    public Matrix rotateMatrixZ(double angle){
        return  rotate(angle, 0, 1);
    }
}


class Vertex {
    double x, y, z;

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void printVert() {
        System.out.println(String.format("|x=%-10f y=%-10f z=%-10f", x, y, z));
    }
}