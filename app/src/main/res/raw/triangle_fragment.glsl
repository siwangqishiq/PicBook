#version 300 es
precision mediump float;
in vec4 f_color;
out vec4 fragColor;
void main()
{
    fragColor = f_color;
    //fragColor = vec4(1.0f , 0.0f , 0.0f, 1.0f);
    //gl_FragColor = f_color;
    //gl_FragColor = vec4(1.0f , 0.0f , 0.0f, 1.0f);
}