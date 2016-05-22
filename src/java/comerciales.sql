-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-05-2016 a las 12:56:05
-- Versión del servidor: 10.1.10-MariaDB
-- Versión de PHP: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `comerciales`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresas`
--

CREATE TABLE `empresas` (
  `id` int(11) NOT NULL,
  `cif` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `nombre` varchar(80) CHARACTER SET utf8 NOT NULL,
  `direccion` varchar(80) CHARACTER SET utf8 NOT NULL,
  `provincia` varchar(30) CHARACTER SET utf8 NOT NULL,
  `poblacion` varchar(30) CHARACTER SET utf8 NOT NULL,
  `cp` varchar(5) CHARACTER SET utf8 NOT NULL,
  `tlf` int(11) NOT NULL,
  `comercial` int(11) NOT NULL,
  `fecha_alta` datetime NOT NULL,
  `contacto` varchar(50) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `empresas`
--

INSERT INTO `empresas` (`id`, `cif`, `nombre`, `direccion`, `provincia`, `poblacion`, `cp`, `tlf`, `comercial`, `fecha_alta`, `contacto`) VALUES
(3, '1212', '121', '12', '121', '12', '12', 1, -1, '2016-05-21 12:43:44', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `login` varchar(15) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Nombre de usuario',
  `nombre` varchar(30) CHARACTER SET utf8 DEFAULT NULL,
  `apellidos` varchar(30) CHARACTER SET utf8 DEFAULT NULL,
  `fnac` datetime DEFAULT NULL COMMENT 'Fecha de Nacimiento',
  `fu` datetime DEFAULT NULL COMMENT 'Fecha Ultimo Acceso',
  `pass` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `nif` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `es_adm` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `login`, `nombre`, `apellidos`, `fnac`, `fu`, `pass`, `nif`, `es_adm`) VALUES
(1, 'admin', 'adm', '', '0000-00-00 00:00:00', NULL, 'e10adc3949ba59abbe56e057f20f883e', '75238231X', b'1'),
(2, 'adm2', 'adm2', '', '0000-00-00 00:00:00', NULL, 'e10adc3949ba59abbe56e057f20f883e', '75238233X', b'0'),
(10, 'pepito', 'Jose', 'Garcíakjkjkjk', '2015-10-04 00:00:00', NULL, '', '76543123Z', b'0'),
(12, 'Jaimito', 'j', 'j', '2016-05-14 00:00:00', NULL, '', '12313', b'0'),
(13, 'prueba', 'p', 'p', '2016-05-20 00:00:00', NULL, '', '1111', b'0'),
(14, 'asdadad', 'asd', 'asd', '2016-05-18 00:00:00', NULL, '', 'asda131313', b'0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `visitas`
--

CREATE TABLE `visitas` (
  `usuarios_id` int(11) NOT NULL,
  `empresas_id` int(11) NOT NULL,
  `fecha` datetime(6) NOT NULL,
  `resultado` mediumtext COLLATE utf8_spanish_ci,
  `motivo` varchar(150) COLLATE utf8_spanish_ci DEFAULT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `empresas`
--
ALTER TABLE `empresas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cif_UNIQUE` (`cif`),
  ADD KEY `comercial` (`cif`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD UNIQUE KEY `login_UNIQUE` (`login`),
  ADD UNIQUE KEY `nif_UNIQUE` (`nif`);

--
-- Indices de la tabla `visitas`
--
ALTER TABLE `visitas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_usuarios_has_empresas_empresas1_idx` (`empresas_id`),
  ADD KEY `fk_usuarios_has_empresas_usuarios_idx` (`usuarios_id`),
  ADD KEY `usuarios_id` (`usuarios_id`),
  ADD KEY `usuarios_id_2` (`usuarios_id`,`empresas_id`,`fecha`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `empresas`
--
ALTER TABLE `empresas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT de la tabla `visitas`
--
ALTER TABLE `visitas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `visitas`
--
ALTER TABLE `visitas`
  ADD CONSTRAINT `fk_usuarios_has_empresas_empresas1` FOREIGN KEY (`empresas_id`) REFERENCES `empresas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_usuarios_has_empresas_usuarios` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
