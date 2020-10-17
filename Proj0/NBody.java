public class NBody {
    /* read the radius*/
    public static double readRadius(String fileName){
        In in = new In(fileName);
        in.readInt(); // Skip the number of planets
        double radius = in.readDouble();  //value the radius
        return radius;
    }
    /* read the bodies*/
    public static Body[] readBodies(String fileName){
        In in = new In(fileName);
        int numOfBodies = in.readInt();   // number of planets
        Body[] bodies = new Body[numOfBodies];
        in.readDouble(); // Skip the value of radius
        for (int i =0; i < numOfBodies; i++){
            bodies[i] = new Body(in.readDouble(), in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble(), in.readString());
        }
        return bodies;
    }
    /* Create an animation and print out information of Universe */
    public static void main(String[] args) {
        /* Parse arguments */     /* let the command line as 157788000.0 25000.0 data/planets.txt  */
        /* put value in Program argument at edit configuration */
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];

        /* Read in data */
        double radius = readRadius(fileName);
        Body[] bodies = readBodies(fileName);
        /* drawing takes place on the offscreen canvas, Only when you call show() drawing get copied from the offscreen canvas to the onscreen canvas*/
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);

        Double[] xForces = new Double[bodies.length];
        Double[] yForces = new Double[bodies.length];

        /* Play the theme to 2001: A Space Odyssey */
        //StdAudio.play( "audio/2001.mid");

        Double currentTime = 0.0;

        while (currentTime < T) {
            /* Calculate the net forces for each Body */
            for (int i = 0; i < bodies.length; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }
            /* Update each Body’s position, velocity, and acceleration */
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.clear();
            /* Draw the background */
            String backgoround = "images/starfield.jpg";
            StdDraw.picture(0, 0, backgoround);
            /* Draw all the body */
            for(Body b :bodies){
                b.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);

            currentTime += dt;
        }

        /* Print the Universe */
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }

    }
}
