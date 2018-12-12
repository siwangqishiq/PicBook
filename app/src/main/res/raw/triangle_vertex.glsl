#version 300 es
precision mediump float;

layout(location = 0) in vec4 a_Position;
layout(location = 1) in vec4 a_Color;
varying vec4 f_color;

void main()
{
    gl_Position = a_Position;
    f_color = a_Color;
}