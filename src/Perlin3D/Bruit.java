package Perlin3D;

public class Bruit {

	/*
	 * Variable qui contient l'unité des vecteurs diagonaux
	 */
	private double unite = 1.0/Math.sqrt(2);
	
	/*
	 * tableau contenant toutes les directions
	 */
	private double[][] gradient = 
	{
		{unite, unite}, {-unite, unite}, {unite, -unite}, {-unite, -unite},
		{1, 0},			{-1, 0},		 {0, 1},		  {0, -1}
	};
	
	/*
	 * Table de permutation qui va permettre de générer les vecteurs
	 */
	private int[] perm = 
	{
		151,160,137,91,90,15,131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,
		142,8,99,37,240,21,10,23,190, 6,148,247,120,234,75,0,26,197,62,94,252,219,
		203,117,35,11,32,57,177,33,88,237,149,56,87,174,20,125,136,171,168, 68,175,
		74,165,71,134,139,48,27,166,77,146,158,231,83,111,229,122,60,211,133,230,220,
		105,92,41,55,46,245,40,244,102,143,54, 65,25,63,161,1,216,80,73,209,76,132,
		187,208, 89,18,169,200,196,135,130,116,188,159,86,164,100,109,198,173,186,3,
		64,52,217,226,250,124,123,5,202,38,147,118,126,255,82,85,212,207,206,59,227,
		47,16,58,17,182,189,28,42,223,183,170,213,119,248,152, 2,44,154,163, 70,221,
		153,101,155,167, 43,172,9,129,22,39,253, 19,98,108,110,79,113,224,232,178,185,
		112,104,218,246,97,228,251,34,242,193,238,210,144,12,191,179,162,241,81,51,145,
		235,249,14,239,107,49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,
		127, 4,150,254,138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,
		156,180
	};
	
	/*
	 * Coordonnées x et y
	 */
	private double x;
	private double y;
	
	/*
	 * Coordonnées de base du point (entier)
	 */
	private int x0;
	private int y0;
	
	/*
	 * Coordonnées temporaire pour la génération des vecteurs
	 */
	private double tempX, tempY, tmp;
	
	/*
	 * Resolution qu'on a
	 */
	private double resolution;
	
	/*
	 * variable contenant les coordonnées entre 0 et 255
	 */
	private int masquageX = 0;
	private int masquageY = 0;
	
	/*
	 * gradient aléatoire
	 */
	private int gradient0, gradient1, gradient2, gradient3;
	
	/*
	 * Vecteurs aléatoire
	 */
	private double vecteur0, vecteur1, vecteur2, vecteur3;
	
	/*
	 * Coefficient d'interpolation
	 */
	private double Cx, Cy;
	
	/*
	 * Lissage des valeurs
	 */
	private double lissage1, lissage2;
	
	private void coordRes() {
		x/=resolution;
		y/=resolution;
	}
	
	private void position() {
		x0 = (int) x;
		y0 = (int) y;
	}
	
	private void masquage() {
		masquageX = x0%256;
		masquageY = y0%256;
	}
	
	private void recupVecteur() {
		
		gradient0 = perm[ (masquageX + perm[masquageY]) % 256 ] % 8;
		gradient1 = perm[ (masquageX+1 + perm[masquageY]) % 256] % 8;
		gradient2 = perm[ (masquageX + perm[ (masquageY+1) % 256]) % 256 ] % 8;
		gradient3 = perm[ (masquageX+1 + perm[ (masquageY+1) % 256]) % 256 ] % 8;
		
		tempX = x-x0;
		tempY = y-y0;
		vecteur0 = gradient[gradient0][0]*tempX + gradient[gradient0][1]*tempY;
		
		tempX = x-(x0+1);
		tempY = y-y0;
		vecteur1 = gradient[gradient1][0]*tempX + gradient[gradient1][1]*tempY;
	
		tempX = x-x0;
		tempY = y-(y0+1);
		vecteur2 = gradient[gradient2][0]*tempX + gradient[gradient2][1]*tempY;
		
		tempX = x-(x0+1);
		tempY = y-(y0+1);
		vecteur3 = gradient[gradient3][0]*tempX + gradient[gradient3][1]*tempY;
	}
	
	private double lissage() {
		tmp = x-x0;
		Cx = 3*tmp*tmp-2*tmp*tmp*tmp;
		//Cx = tmp;
		//Cx = Math.pow(tmp, 4)-6*Math.pow(tmp, 2)+3;
		
		lissage1 = vecteur0 + Cx*(vecteur1-vecteur0);
		lissage2 = vecteur2 + Cx*(vecteur3-vecteur2);
		
		tmp = y-y0;
		/*
		 * Interpolation linéaire
		 */
		//Cy = tmp;
		/*
		 * Interpolation cubique
		 */
		Cy = 3*tmp*tmp-2*tmp*tmp*tmp;
		/*
		 * Interpolation Hermite
		 */
		//Cy = Math.pow(tmp, 4)-6*Math.pow(tmp, 2)+3;
		
		return lissage1 + Cy*(lissage2-lissage1);
	}
	
	public double Noise(double x, double y, double d) {
		this.x = x;
		this.y = y;
		this.resolution = d;
		
		coordRes();
		
		position();
		
		masquage();
		
		recupVecteur();
		
		return lissage();
	}
}

/*
public class Bruit {

	public double Noise(double x, double y, double z, int resolution) {
		int X = (int)Math.floor(x) % 256,                  // FIND UNIT CUBE THAT
			Y = (int)Math.floor(y) % 256,                  // CONTAINS POINT.
			Z = (int)Math.floor(z) % 256;
		
		x -= Math.floor(x);                                // FIND RELATIVE X,Y,Z
		y -= Math.floor(y);                                // OF POINT IN CUBE.
		z -= Math.floor(z);
		
		double u = fade(x),                                // COMPUTE FADE CURVES
				v = fade(y),                                // FOR EACH OF X,Y,Z.
				w = fade(z);
		
		int A = p[X  ]+Y, AA = p[A]+Z, AB = p[A+1]+Z,      // HASH COORDINATES OF
			B = p[X+1]+Y, BA = p[B]+Z, BB = p[B+1]+Z;      // THE 8 CUBE CORNERS,
		

		return lerp(w, lerp(v, lerp(u, grad(p[AA  ], x, y, z),  // AND ADD
				grad(p[BA  ], x-1, y, z)), // BLENDED
				lerp(u, grad(p[AB  ], x, y-1, z),  // RESULTS
						grad(p[BB  ], x-1, y-1, z))),// FROM  8
				lerp(v, lerp(u, grad(p[AA+1], x, y, z-1),  // CORNERS
						grad(p[BA+1], x-1, y, z-1)), // OF CUBE
						lerp(u, grad(p[AB+1], x, y-1, z-1),
								grad(p[BB+1], x-1, y-1, z-1))));
	}
	
	static double fade(double t) {
		return t * t * t * (t * (t * 6 - 15) + 10);
	}
	static double lerp(double t, double a, double b) {
		return a + t * (b - a);
	}
	
	static double grad(int hash, double x, double y, double z) {
		int h = hash & 15;                      // CONVERT LO 4 BITS OF HASH CODE
		double u = h<8 ? x : y,                 // INTO 12 GRADIENT DIRECTIONS.
				v = h<4 ? y : h==12||h==14 ? x : z;
		return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
	}
	
	static final int p[] = new int[512], permutation[] = { 151,160,137,91,90,15,
			131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
			190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
			88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
			77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
			102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
			135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
			5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
			223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
			129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
			251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
			49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
			138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
	};
	static { for (int i=0; i < 256 ; i++) p[256+i] = p[i] = permutation[i]; }
}
*/