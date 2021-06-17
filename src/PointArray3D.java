// Etape 1 :
// Importation des packages Java 2
import java.applet.Applet;
import java.awt.*;

// Etape 2 :
// Importation des packages Java 3D
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class PointArray3D extends Applet {

  public PointArray3D() {
    this.setLayout(new BorderLayout());

    // Etape 3 :
    // Creation du Canvas 3D
    Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    this.add(canvas3D, BorderLayout.CENTER);

    // Etape 4 :
    // Creation d'un objet SimpleUniverse
    SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

    // Etape 5 :
    // Positionnement du point d'observation pour avoir une vue correcte de la scene 3D
    simpleU.getViewingPlatform().setNominalViewingTransform();

    // Etape 6 :
    // Creation de la scene 3D qui contient tous les objets 3D que l'on veut visualiser
    BranchGroup scene = createSceneGraph();

    // Etape 7 :
    // Compilation de la scene 3D
    scene.compile();

    // Etape 8:
    // Attachement de la scene 3D a l'objet SimpleUniverse
    simpleU.addBranchGraph(scene);
  }

  /**
   * Creation de la scene 3D qui contient tous les objets 3D
   * @return scene 3D
   */
  public BranchGroup createSceneGraph() {
    // Creation de l'objet parent qui contiendra tous les autres objets 3D
    BranchGroup parent = new BranchGroup();

    /************ Partie de code concernant l'animation *************/
    /* Elle sera expliquee en details dans les chapitres relatifs aux
    transformations geometriques et aux animations */
    TransformGroup objSpin = new TransformGroup();
    objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    Alpha rotationAlpha = new Alpha(-1, 4000);
    RotationInterpolator rotator =
        new RotationInterpolator(rotationAlpha, objSpin);
    //BoundingSphere bounds = new BoundingSphere();
    //rotator.setSchedulingBounds(bounds);
    //objSpin.addChild(rotator);
    /*************** Fin de la partie relative a l'animation ***************/

    // On ajoute l'objet geometrique a la scene 3D, les points sont de couleur
    // jaune
    objSpin.addChild(new EnsemblePoints(50, Color.yellow));
    parent.addChild(objSpin);

    return parent;
  }

  /**
   * Objet geometrique qui represente un ensemble de points isoles en forme de
   * cercle
   */
  class EnsemblePoints extends Shape3D {

    /**
     * Constructeur
     * @param nbPoints nombre de points
     */
    public EnsemblePoints(int nbPoints, Color color) {
      Point3f points[] = new Point3f[nbPoints];
      Color3f colors[] = new Color3f[nbPoints];

      for (int i = 0 ; i < nbPoints ; i++) {
        points[i] = new Point3f(0.5f*(float)Math.cos(2*i*Math.PI/nbPoints),
                                0.5f*(float)Math.sin(2*i*Math.PI/nbPoints),
                                0f);

        colors[i] = new Color3f(color);
      } // fin for (int i = 0 ; i < nbPoints ; i++)

      // Construction de l'objet geometrique PointArray
      PointArray pointArray = new PointArray(nbPoints,
                                             PointArray.COORDINATES |
                                             PointArray.COLOR_3);
      pointArray.setCoordinates(0, points);
      pointArray.setColors(0, colors);

      this.setGeometry(pointArray);
    } // fin constructeur
  } // fin class EnsemblePoints extends Shape3D

  /**
   * Etape 9 :
   * Methode main() nous permettant d'utiliser cette classe comme une applet
   * ou une application.
   * @param args
   */
  public static void main(String[] args) {
    Frame frame = new MainFrame(new PointArray3D(), 256, 256);
  }
}