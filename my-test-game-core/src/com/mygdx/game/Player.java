package com.mygdx.game;

import static utils.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player {
	private World world;
	private int x;
	private int y;
	private SpriteBatch batch;
	private Body body;
	private boolean jump;

	Player(World world, int x, int y, SpriteBatch batch){
		this.batch = batch;
		this.world = world;
		this.x = x;
		this.y = y;
		jump = false;
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
	
	public Body getBody() {
		body = createBox(x,y, 32, 64, false);
		return body;
	}
	public void inputUpdate(float delta) {
		int horizontalForce = 0;

		
		if(body.getLinearVelocity().y == 0) {
			jump = false;
		}
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontalForce -= 10;

		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontalForce += 10;
			
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !jump) {
			
			body.applyForceToCenter(0, 100000000, false);
			jump = true;
			
		}
     
		body.setLinearVelocity(horizontalForce * 10, body.getLinearVelocity().y );
		
	}

	
}
