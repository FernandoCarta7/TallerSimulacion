import random

# Constantes
COSTO_CROQUETAS = 800  # Costo por kg de croquetas
VALOR_VENTAS = 1500    # Valor de venta por kg
DIAS_SIMULACION = 360

# Variables
cantidadPaquetes = 0  # Paquetes de 10 kg
contadorPaquetesSimulacion = [0, 0, 0, 0, 0]
gananciasPorPaquete = [0, 0, 0, 0, 0]
mayorGanancia = 0  # Almacena la máxima ganancia
posicionGanancia = -1
demanda = [0, 0]
inventario = 0
inventarioEnKG = 0
donacion = 0
acumuladoDonacion = [0, 0]
acumuladoVenta = [0, 0]
gananciasNetas = [0, 0]

# Funciones
def get_ganancias(cantidad_paquetes, valor_venta, costo_croqueta):
    return ((cantidad_paquetes * valor_venta * 10) - (cantidad_paquetes * costo_croqueta * 10)) / 1000

def get_paquete_optimo(posicion):
    opciones = {
        0: "La producción de 5 paquetes de 10 kg es la mejor opción",
        1: "La producción de 10 paquetes de 10 kg es la mejor opción",
        2: "La producción de 12 paquetes de 10 kg es la mejor opción",
        3: "La producción de 15 paquetes de 10 kg es la mejor opción",
    }
    print(opciones.get(posicion, "La producción de 20 paquetes de 10 kg es la mejor opción"))
    return [5, 10, 12, 15, 20][posicion]

def simulacion_montecarlo_demanda():
    aleatorio = random.random()
    if aleatorio <= 0.25:
        return 5
    elif aleatorio <= 0.40:
        return 10
    elif aleatorio <= 0.75:
        return 12
    elif aleatorio <= 0.875:
        return 15
    else:
        return 20

# SIMULACIÓN DE MONTE CARLO PARA DETERMINAR CANTIDAD OPTIMA DE PRODUCCIÓN
for _ in range(DIAS_SIMULACION):
    aleatorio = random.random()
    
    if aleatorio <= 0.25:
        cantidadPaquetes = 5
        contadorPaquetesSimulacion[0] += 1
        gananciasPorPaquete[0] += get_ganancias(cantidadPaquetes, VALOR_VENTAS, COSTO_CROQUETAS)
    elif aleatorio <= 0.40:
        cantidadPaquetes = 10
        contadorPaquetesSimulacion[1] += 1
        gananciasPorPaquete[1] += get_ganancias(cantidadPaquetes, VALOR_VENTAS, COSTO_CROQUETAS)
    elif aleatorio <= 0.75:
        cantidadPaquetes = 12
        contadorPaquetesSimulacion[2] += 1
        gananciasPorPaquete[2] += get_ganancias(cantidadPaquetes, VALOR_VENTAS, COSTO_CROQUETAS)
    elif aleatorio <= 0.875:
        cantidadPaquetes = 15
        contadorPaquetesSimulacion[3] += 1
        gananciasPorPaquete[3] += get_ganancias(cantidadPaquetes, VALOR_VENTAS, COSTO_CROQUETAS)
    else:
        cantidadPaquetes = 20
        contadorPaquetesSimulacion[4] += 1
        gananciasPorPaquete[4] += get_ganancias(cantidadPaquetes, VALOR_VENTAS, COSTO_CROQUETAS)

# Identificar la mayor ganancia
for i in range(len(gananciasPorPaquete)):
    if gananciasPorPaquete[i] > mayorGanancia:
        mayorGanancia = gananciasPorPaquete[i]
        posicionGanancia = i

cantidadPaquetes = get_paquete_optimo(posicionGanancia)

# Simulación con demanda e inventario
for j in range(2):
    for _ in range(DIAS_SIMULACION):
        demanda[0] = simulacion_montecarlo_demanda()
        demanda[1] = demanda[0] * 1.2

        inventario += cantidadPaquetes

        aleatorio = random.random()

        if inventario > demanda[j]:
            acumuladoVenta[j] += demanda[j]
            if aleatorio < 0.45:
                inventario = 0
            else:
                inventario -= demanda[j]
        else:
            acumuladoVenta[j] += inventario
            inventario = 0

        if inventario > 0:
            inventarioEnKG = inventario * 10
            donacion = inventarioEnKG / (2 if j == 0 else 4)
            acumuladoDonacion[j] += donacion

# Calcular ganancias netas
for i in range(len(acumuladoDonacion)):
    gananciasNetas[i] = (acumuladoVenta[i] * 10 * VALOR_VENTAS) - COSTO_CROQUETAS * (acumuladoVenta[i] + acumuladoDonacion[i] * 2)

# Resultados
print("Ventas totales")
print(f"En {DIAS_SIMULACION} días se donó: {acumuladoDonacion[0]} kg")
print(f"En {DIAS_SIMULACION} días se vendió: {acumuladoVenta[0] * 10} kg")
print(f"Ganancias netas: {gananciasNetas[0] / 1000000} millones de pesos")

print("******************************\n")
print("Ventas totales con aumento del 20% en ganancias")
print(f"En {DIAS_SIMULACION} días se donó: {acumuladoDonacion[1] * 2} kg")
print(f"En {DIAS_SIMULACION} días se vendió: {acumuladoVenta[1] * 10} kg")
print(f"Ganancias netas: {gananciasNetas[1] / 1000000} millones de pesos")
