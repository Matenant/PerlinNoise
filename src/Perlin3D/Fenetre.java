package Perlin3D;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

@SuppressWarnings("serial")
public class Fenetre extends Applet
{
	Bruit n;
	Background background;
	public Fenetre()
	{
		super();
		n = new Bruit();
		setLayout(new BorderLayout());
		Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		add("Center", canvas3D);
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		simpleU.getViewingPlatform().setNominalViewingTransform();
		BranchGroup scene = createSceneGraph(simpleU);
		
		BoundingSphere worldBounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);

		background = new Background();
		background.setColor(new Color3f(1.0f, 1.0f, 1.0f));
		background.setCapability(Background.ALLOW_COLOR_WRITE);
		background.setApplicationBounds(worldBounds);
		scene.addChild(background);
		
		scene.compile();
		simpleU.getViewer().getView().setBackClipDistance(1000);
		simpleU.addBranchGraph(scene);
	}

	public BranchGroup createSceneGraph(SimpleUniverse su)
	{
		BranchGroup objRoot=new BranchGroup();

		Appearance app_rouge = new Appearance();
		ColoringAttributes rouge = new ColoringAttributes();
		rouge.setColor(1.0f, 0.1f, 0.1f);
		rouge.setShadeModel(ColoringAttributes.NICEST);
		app_rouge.setColoringAttributes(rouge);

		Appearance app_bleu = new Appearance();
		ColoringAttributes bleu = new ColoringAttributes();
		bleu.setColor(0.1f, 0.1f, 1.0f);
		bleu.setShadeModel(ColoringAttributes.NICEST);
		app_bleu.setColoringAttributes(bleu);
		PolygonAttributes poly = new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0);
		app_bleu.setPolygonAttributes(poly);
		LineAttributes line = new LineAttributes(1f, LineAttributes.PATTERN_DASH_DOT, false);
		app_bleu.setLineAttributes(line);

		TransformGroup obj = su.getViewingPlatform().getViewPlatformTransform();

		KeyNavigatorBehavior keyNavigatorBehavior = new KeyNavigatorBehavior(obj);

		keyNavigatorBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000));
		objRoot.addChild(keyNavigatorBehavior);
		
		Appearance app=new Appearance();
		PolygonAttributes polyAttrib = new PolygonAttributes();
		polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);polyAttrib.setPolygonMode(PolygonAttributes.POLYGON_LINE);
		app.setPolygonAttributes(polyAttrib);

		TransformGroup objSpin = new TransformGroup();
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		float k = 0.0f, m = 0.0f;
		for(int i = 0; i < 150; i++) {
			for(int j = 0; j < 150; j++) {
				Point3f[] pts = new Point3f[3];
				pts[0] = new Point3f(m, (float) (n.Noise(j, i, 10)*2), k);
				pts[1] = new Point3f(m, (float) (n.Noise(j, i+1, 10)*2), k+0.4f);
				pts[2] = new Point3f(m+0.4f, (float) (n.Noise(j+1, i+1, 10)*2), k+0.4f);
				Shape3D shape = new Shape3D(triangle2(pts, Color.red), app);
				objSpin.addChild(shape);
				
				Point3f[] pts2 = new Point3f[3];
				pts2[0] = new Point3f(m, (float) (n.Noise(j, i, 10)*2), k);
				pts2[1] = new Point3f(m+0.4f, (float) (n.Noise(j+1, i+1, 10)*2), k+0.4f);
				pts2[2] = new Point3f(m+0.4f, (float) (n.Noise(j+1, i, 10)*2), k);
				
				Shape3D shape2 = new Shape3D(triangle2(pts2, Color.red), app);
				objSpin.addChild(shape2);
				
				m += 0.4f;
			}
			m = 0.0f;
			k += 0.4f;
		}
		
		objRoot.addChild(objSpin);
		return objRoot;
	}
	
	private Geometry triangle2(Point3f[] pts, Color color) {
		Point3f points[] = pts;
		Color3f colors[] = new Color3f[3];

		colors[0] = new Color3f(color);
		colors[1] = new Color3f(color);
		colors[2] = new Color3f(color);

		TriangleArray trStrip=new TriangleArray (3, TriangleArray.COORDINATES | TriangleArray.COLOR_3);
		trStrip.setCoordinates(0, points);
		trStrip.setColors(0, colors);
		
		return trStrip;
	}
}
