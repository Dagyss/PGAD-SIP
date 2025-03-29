const path = require('path')

module.exports = {
  // Ruta base para producción (importante para recursos estáticos)
  publicPath: '/',
  
  // Directorio de salida del build
  outputDir: path.resolve(__dirname, '../backend/src/main/resources/static'),
  
  // Directorio para assets (js, css, img)
  assetsDir: 'static',
  
  // Nombre del archivo index HTML
  indexPath: 'index.html',
  
  // Configuración opcional para el servidor de desarrollo
  devServer: {
    port: 3000,
    proxy: {
      '^/api': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      }
    }
  },
  
  // Configuración de optimización
  configureWebpack: {
    optimization: {
      splitChunks: {
        chunks: 'all'
      }
    }
  },
  
  // Opcional: Generar source maps para debugging
  productionSourceMap: true
}