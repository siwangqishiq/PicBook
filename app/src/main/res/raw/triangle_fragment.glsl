#version es 300
precision mediump float;
varying vec4 f_color;
out vec4 fragColor;
void main()
{
    fragColor = f_color;
}