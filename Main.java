class Main {
    public static void main(String[] args) {
        // Создать одномерный массив c типа int. Заполнить его нечётными числами от 3 до 25 включительно в порядке возрастания.
        int[] c = new int[12];
        for (int i = 0; i < 12; ++i) {
            c[i] = 3+i*2;
        }
        
        // Создать одномерный массив x типа double. Заполнить его 15-ю случайными числами в диапазоне от -6.0 до 8.0.
        double[] x = new double[15];
        for (int i = 0; i < 15; ++i) {
            x[i] = -6 + Math.random() * (8+6);
        }

        double[][] a = new double[12][15];
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < 15; ++j) {
                switch (c[i]) {
                    case 19:
                        a[i][j] = Math.pow(Math.atan(0.2*(x[j]+1)/14), 0.25/(Math.asin(Math.pow(Math.E, -Math.abs(x[j])))-1));
                        break;
                    case 5:
                    case 9:
                    case 11:
                    case 13:
                    case 17:
                    case 23:
                        a[i][j] = Math.cos(Math.cos(Math.cbrt(x[j])));
                        break;
                    default:
                        a[i][j] = Math.pow(0.25 - Math.pow(Math.cbrt(Math.pow(x[j], x[j]-3/4)), 3+Math.cos(Math.cbrt(x[j]))), Math.asin(Math.pow(Math.E, Math.cbrt(-Math.pow(4/Math.abs(x[j]),x[j])))));
                        break;
                }
            }
        }
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < 15; ++j) {
                System.out.print(String.format("%.4f ", a[i][j]));
                // System.out.print(a[i][j]);
            }
            System.out.println();
        }
        double xx = -5.5;
        System.out.println(Math.asin(Math.pow(Math.E, Math.cbrt(-Math.pow(4/Math.abs(xx),xx)))));
        System.out.println(Math.pow(Math.cbrt(Math.pow(xx, xx-3/4)), 3+Math.cos(Math.cbrt(xx))));
        System.out.println(Math.pow(Math.cbrt(Math.pow(xx, xx-3/4)), xx));
        System.out.println(Math.pow(0.25 - Math.pow(Math.cbrt(Math.pow(xx, xx-3/4)), 3+Math.cos(Math.cbrt(xx))), Math.asin(Math.pow(Math.E, Math.cbrt(-Math.pow(4/Math.abs(xx),xx))))));

        // for (int i = 0; i < 15; ++i)
        // System.out.println(x[i]);
    }
}