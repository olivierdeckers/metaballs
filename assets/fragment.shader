#ifdef GL_ES
precision mediump float;
#endif

#define NUM_BALLS 50
uniform vec3 u_balls[NUM_BALLS]; //.xy is position .z is radius
uniform vec3 u_ball_colors[NUM_BALLS];

vec3 energyField(vec2 p, float gooeyness, float iso)
{
    float en = 0.0;
    vec3 color = vec3(0.0, 0.0, 0.0);

    for(int i=0; i<NUM_BALLS; ++i)
    {
        float radius = u_balls[i].z;
        float denom =  max(0.0001, pow(length(u_balls[i].xy - p), gooeyness));
        en += (radius / denom);
        color += u_ball_colors[i] * pow(radius/denom, 3.0);
    }
    if (en > iso)
    {
    	float max = max(color.x, color.y);
    	max = max(color.z, max);
    	return color * 1.0/max;
    }
    else {
    	return vec3(1.0, 1.0, 1.0);
    }
}


void main()
{
    vec3 color = energyField(gl_FragCoord.xy, 1.0, 1.2);
    gl_FragColor = vec4(color, 1.0);
}