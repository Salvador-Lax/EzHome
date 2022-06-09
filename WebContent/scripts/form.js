var arr;
var newSelect;

function getVal(sel) {
  $("#secondList").empty();

  if (sel.value === "") {
    return false;
  } else {
    if ($("#tipo").length) {
      newSelect = $("<select>")
        .appendTo("#secondList")
        .attr("name", "subtipo")
        .attr("id", "subtipo")
        .addClass("form-select")
        .attr("required", true);
    } else {
      newSelect = $("<select>")
        .appendTo("#secondList")
        .attr("name", "filtroSubtipo")
        .attr("id", "filtroSubtipo")
        .addClass("form-select");
    }

    if (sel.value === "Vivienda" && $("#tipo").length) {
      arr = [
        { val: "titulo", text: "Elija un Subtipo" },
        { val: "Estudio", text: "Estudio" },
        { val: "Apartamento", text: "Apartamento" },
        { val: "Piso", text: "Piso" },
        { val: "Ático", text: "Ático" },
        { val: "Buhardilla", text: "Buhardilla" },
        { val: "Bajo", text: "Bajo" },
        { val: "Bajo con jardín", text: "Bajo con jardín" },
        { val: "Loft", text: "Loft" },
      ];
    }
    if (sel.value === "Vivienda" && !$("#tipo").length) {
      arr = [
        { val: "titulo", text: "Filtrar Subtipo" },
        { val: "", text: "Sin filtro" },
        { val: "Estudio", text: "Estudio" },
        { val: "Apartamento", text: "Apartamento" },
        { val: "Piso", text: "Piso" },
        { val: "Ático", text: "Ático" },
        { val: "Buhardilla", text: "Buhardilla" },
        { val: "Bajo", text: "Bajo" },
        { val: "Bajo con jardín", text: "Bajo con jardín" },
        { val: "Loft", text: "Loft" },
      ];
    }
    if (sel.value === "Garaje" && $("#tipo").length) {
      arr = [
        { val: "titulo", text: "Elija un Subtipo" },
        { val: "Cobertizo", text: "Cobertizo" },
        { val: "Cerrado", text: "Cerrado" },
        { val: "Semicerrado", text: "Semicerrado" },
      ];
    }
    if (sel.value === "Garaje" && !$("#tipo").length) {
      arr = [
        { val: "titulo", text: "Filtrar Subtipo" },
        { val: "", text: "Sin filtro" },
        { val: "Cobertizo", text: "Cobertizo" },
        { val: "Cerrado", text: "Cerrado" },
        { val: "Semicerrado", text: "Semicerrado" },
      ];
    }
    if (sel.value === "Local" && $("#tipo").length) {
      arr = [
        { val: "titulo", text: "Elija un Subtipo" },
        { val: "Almacen", text: "Almacen" },
        { val: "Tienda especializada", text: "Tienda especializada" },
        { val: "Departamento", text: "Departamento" },
      ];
    }
    if (sel.value === "Local" && !$("#tipo").length) {
      arr = [
        { val: "titulo", text: "Filtrar Subtipo" },
        { val: "", text: "Sin filtro" },
        { val: "Almacen", text: "Almacen" },
        { val: "Tienda especializada", text: "Tienda especializada" },
        { val: "Departamento", text: "Departamento" },
      ];
    }
    if (sel.value === "Oficina" && $("#tipo").length) {
      arr = [
        { val: "titulo", text: "Elija un Subtipo" },
        { val: "Oficina pequeña", text: "Oficina pequeña" },
        { val: "Oficina moderna", text: "Oficina moderna" },
        { val: "Oficina cerrada", text: "Oficina cerrada" },
        { val: "Oficina abierta", text: "Oficina abierta" },
      ];
    }
    if (sel.value === "Oficina" && !$("#tipo").length) {
      arr = [
        { val: "titulo", text: "Filtrar Subtipo" },
        { val: "", text: "Sin filtro" },
        { val: "Oficina pequeña", text: "Oficina pequeña" },
        { val: "Oficina moderna", text: "Oficina moderna" },
        { val: "Oficina cerrada", text: "Oficina cerrada" },
        { val: "Oficina abierta", text: "Oficina abierta" },
      ];
    }
    if (sel.value === "Trastero" && $("#tipo").length) {
      arr = [
        { val: "titulo", text: "Elija un Subtipo" },
        { val: "Trastero inteligente", text: "Trastero inteligente" },
        { val: "Guardamuebles", text: "Guardamuebles" },
        { val: "Minitrastero", text: "Minitrastero" },
        { val: "Trastero convencional", text: "Trastero convencional" },
      ];
    }
    if (sel.value === "Trastero" && !$("#tipo").length) {
      arr = [
        { val: "titulo", text: "Filtrar Subtipo" },
        { val: "", text: "Sin filtro" },
        { val: "Trastero inteligente", text: "Trastero inteligente" },
        { val: "Guardamuebles", text: "Guardamuebles" },
        { val: "Minitrastero", text: "Minitrastero" },
        { val: "Trastero convencional", text: "Trastero convencional" },
      ];
    }

    $(arr).each(function () {
      if (this.val === "titulo") {
        newSelect.append(
          $("<option>")
            .attr("disabled", "disabled")
            .text(this.text)
            .attr("selected", "selected")
        );
      } else {
        newSelect.append($("<option>").attr("value", this.val).text(this.text));
      }
    });
  }
}
