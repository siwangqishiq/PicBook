#version 300 es
precision mediump float;

layout(location = 0) in vec4 a_position;
layout(location = 1) in vec4 a_color;

out vec4 f_color;

void main()
{
    gl_Position = a_position;
    f_color = a_color;
}