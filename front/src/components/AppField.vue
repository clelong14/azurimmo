<template>
  <div class="field">
    <label class="field-label">
      {{ label }}
      <span v-if="required" class="req">*</span>
    </label>
    <textarea
        v-if="type === 'textarea'"
        class="field-input field-textarea"
        :value="modelValue"
        :placeholder="placeholder"
        :required="required"
        v-bind="attrs"
        @input="$emit('update:modelValue', $event.target.value)"
    />
    <select
        v-else-if="type === 'select'"
        class="field-input field-select"
        :value="modelValue"
        :required="required"
        v-bind="attrs"
        @change="$emit('update:modelValue', $event.target.value)"
    >
      <option value="">{{ placeholder || 'Sélectionner…' }}</option>
      <slot />
    </select>
    <input
        v-else
        class="field-input"
        :type="type"
        :value="modelValue"
        :placeholder="placeholder"
        :required="required"
        v-bind="attrs"
        @input="$emit('update:modelValue', $event.target.value)"
    />
  </div>
</template>

<script setup>
import { useAttrs } from 'vue'

defineOptions({ inheritAttrs: false })

// Forward native attributes (min, max, step, etc.) to the actual control.
const attrs = useAttrs()

defineProps({ label: String, type: { default: 'text' }, modelValue: [String, Number], placeholder: String, required: Boolean })
defineEmits(['update:modelValue'])
</script>

<style scoped>
.field { display: flex; flex-direction: column; gap: 0.35rem; }
.field-label {
  font-size: 0.8rem; font-weight: 500;
  color: var(--text-secondary);
}
.req { color: var(--red); margin-left: 2px; }
.field-input {
  padding: 0.5rem 0.75rem;
  background: var(--bg-primary);
  border: 1px solid var(--border);
  border-radius: var(--radius);
  color: var(--text-primary);
  font-size: 0.875rem;
  transition: border-color 0.15s, box-shadow 0.15s;
  width: 100%;
  appearance: none;
}
.field-input:focus {
  border-color: var(--accent);
  box-shadow: 0 0 0 3px var(--accent-bg);
  outline: none;
}
.field-input::placeholder { color: var(--text-muted); }
.field-textarea { resize: vertical; min-height: 80px; line-height: 1.5; }
.field-select { cursor: pointer;
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 20 20'%3e%3cpath stroke='%236b7280' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='m6 8 4 4 4-4'/%3e%3c/svg%3e");
  background-repeat: no-repeat; background-position: right 0.5rem center; background-size: 1rem;
  padding-right: 2rem;
}
</style>