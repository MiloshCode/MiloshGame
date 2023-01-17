package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.physics.box2d.Body;

import com.badlogic.gdx.physics.box2d.BodyDef;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import com.badlogic.gdx.physics.box2d.PolygonShape;

import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.utils.ScreenUtils;

import static utils.Constants.PPM;

public class MyGdxGame extends ApplicationAdapter {
	
	private boolean DEBUG = false;
	private final float SCALE = 2.0f;

	Texture img;

	private OrthographicCamera camera;

	private Box2DDebugRenderer b2dr;

	private World world;
	private Player player;
	private Body platform;
	private Body pBody;
	
	private SpriteBatch batch; 
	private Texture tex;


	
	private final float frameTime = 1 / 5f;
	


	@Override

	public void create () {

		batch = new SpriteBatch();

		camera = new OrthographicCamera();

		float height = Gdx.graphics.getHeight();

		float width = Gdx.graphics.getWidth();

		camera.setToOrtho(false, width / SCALE, height / SCALE);

		world = new World(new Vector2(0,-100f), false);

		b2dr = new Box2DDebugRenderer();
		

		player = new Player(world, 0, 50, batch);
		
		pBody = player.getBody();

		platform = createBox(0, 0, 128, 32, true);
		
		batch = new SpriteBatch();

		tex = new Texture("Images/Ghos.png");
		



	}

	@Override

	public void render () {

		

		// Render
		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		
		b2dr.render(world, camera.combined.scl(PPM));
		update(Gdx.graphics.getDeltaTime());
		
		
		batch.begin();
		batch.draw(tex, pBody.getPosition().x - 15, pBody.getPosition().y - 31);

		batch.end();

	}

	@Override

	public void resize(int width, int height) {

		camera.setToOrtho(false, width / SCALE, height / SCALE);
		
	}

	@Override

	public void dispose () {

		world.dispose();
		batch.dispose();
		b2dr.dispose();


	}

	public void update(float delta) {

		world.step(1 / 60f, 6, 2);
		player.inputUpdate(delta);
		batch.setProjectionMatrix(camera.combined);
		cameraUpdate(delta);

	}
	
	public void cameraUpdate(float delta) {

		// Centers camera on player

		Vector3 position = camera.position;

		position.x = pBody.getPosition().x * PPM;

		position.y = pBody.getPosition().y * PPM;

		camera.position.set(position);

		camera.update();

	}

	public Body createBox(int x, int y, int width, int height, boolean isStatic) {

		Body pBody;

		BodyDef def = new BodyDef();

		def.position.set(x / PPM, y / PPM);

		def.fixedRotation = true;

		if(isStatic)

			def.type = BodyDef.BodyType.StaticBody;

		else

			def.type = BodyDef.BodyType.DynamicBody;

		// Initializes body, puts it into the world

		pBody = world.createBody(def);

		// Gives the body a shape (shape.setAsBox measures size from the middle)

		PolygonShape shape = new PolygonShape();

		shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

		pBody.createFixture(shape, 1.0f);

		shape.dispose();
		

		return pBody;

	}

}
