## RayTracer

![Alt text](cover_image.png)

This project is a Java Port of [Ray Tracing in One Weekend by Peter Shirley](https://www.realtimerendering.com/raytracing/Ray%20Tracing%20in%20a%20Weekend.pdf). The original code is [here](https://github.com/RayTracing/raytracing.github.io).

It is ray tracer solely on CPU written in Java, with some minor changes due to the differences of the sintax in Java and C++, allowing to specify the size and number of samples per pixel in the image.

This ray tracer uses Java Swing to help us visualize the renderization of the image in real time implementing Multithreading. It consists of three different kinds of Renderers and three multithreading ray tracers.

The different ray tracers classes differ on their approach with the use of multithreading:
- JThreadPoolMTRayTracer: applies the ThreadPool provided in base Java using the Executors class
- SemaphoreMTRayTracer: applies the Semaphore class provided in base Java
- ThreadPoolMTRayTracer: applies a custom ThreadPool with the use of RenderThreads

### Renderers
As mentioned before, there are three Renderers which help us observe the renderization of the image. Those are:

- PixelRenderer: each Worker generated in the ThreadPool (or Semaphore) will paint a single Pixel

![Alt text](PixelRenderer.gif)

- RowRenderer: each Worker will paint a row

![Alt text](RowRenderer.gif)

- BatchRenderer: each Worker will paint one or more rows (can be specified the number of rows painted)

![Alt text](BatchRenderer)

