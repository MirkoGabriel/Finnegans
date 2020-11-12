-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 12-11-2020 a las 03:34:05
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `finnegans`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `actividades`
--

CREATE TABLE `actividades` (
  `nombre` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `tiempo` int(11) DEFAULT NULL,
  `cupo` int(11) DEFAULT NULL,
  `tipo` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `dias` varchar(50) CHARACTER SET latin1 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `actividades`
--

INSERT INTO `actividades` (`nombre`, `tiempo`, `cupo`, `tipo`, `dias`) VALUES
('yoga', 45, 20, 'relajacion', 'lunes jueves'),
('actuacion', 60, 10, 'entretenimiento', 'martes viernes'),
('ensayo musical', 50, 5, 'entretenimiento', 'miercoles'),
('metegol', 10, 2, 'recreacion', 'lunes martes miercoles jueves viernes'),
('cyberjuegos-fifa', 10, 4, 'recreacion', 'lunes martes miercoles jueves viernes'),
('cyberjuegos-mathemats', 10, 4, 'recreacion', 'lunes martes miercoles jueves viernes');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cont`
--

CREATE TABLE `cont` (
  `id` int(11) NOT NULL,
  `flag` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cont`
--

INSERT INTO `cont` (`id`, `flag`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

CREATE TABLE `reservas` (
  `idUser` int(11) DEFAULT NULL,
  `dia` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `actividad` varchar(50) CHARACTER SET latin1 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `password` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `tipo` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `tiempo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `nombre`, `password`, `tipo`, `tiempo`) VALUES
(1, 'mirko', '1234', 'Nerd', 250),
(2, 'mery', 'bts', 'Sensei', 175),
(3, 'erika', '1256', 'Neonato', 100),
(4, 'pepe', 'mario', 'Sensei', 175);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
